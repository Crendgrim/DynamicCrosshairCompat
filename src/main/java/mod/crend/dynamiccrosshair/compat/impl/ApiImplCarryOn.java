package mod.crend.dynamiccrosshair.compat.impl;

import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
//? if carryon {
import tschipp.carryon.Constants;
import tschipp.carryon.common.carry.CarryOnData;
import tschipp.carryon.common.carry.CarryOnDataManager;
import tschipp.carryon.common.config.ListHandler;
//?}
import java.util.UUID;

public class ApiImplCarryOn implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "carryon";
	}

	//? if carryon {
	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public boolean forceInvalidate(CrosshairContext context) {
		CarryOnData carry = CarryOnDataManager.getCarryData(context.getPlayer());
		return carry.isCarrying(CarryOnData.CarryType.BLOCK);
	}

	// Logic taken from mod

	public static boolean canCarryGeneral(ClientPlayerEntity player, Vec3d pos) {
		if (player.getMainHandStack().isEmpty() && player.getOffHandStack().isEmpty()) {
			if (player.getPos().distanceTo(pos) > Constants.COMMON_CONFIG.settings.maxDistance) {
				return false;
			} else {
				CarryOnData carry = CarryOnDataManager.getCarryData(player);
				return !carry.isCarrying() && carry.isKeyPressed() && player.age != carry.getTick();
			}
		} else {
			return false;
		}
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockPos pos = context.getBlockPos();
		CarryOnData carry = CarryOnDataManager.getCarryData(context.getPlayer());
		if (carry.isCarrying()) {
			if (context.getPlayer().age != carry.getTick()) {
				if (carry.isCarrying(CarryOnData.CarryType.BLOCK)) {
					BlockState state = carry.getBlock();
					Direction offset = context.getBlockHitSide();
					ItemPlacementContext placementContext = new ItemPlacementContext(context.getPlayer(), Hand.MAIN_HAND, ItemStack.EMPTY, BlockHitResult.createMissed(context.getPlayer().getPos(), offset, pos));
					if (!context.getBlockState().canReplace(placementContext)) {
						pos = pos.offset(offset);
						placementContext = new ItemPlacementContext(context.getPlayer(), Hand.MAIN_HAND, ItemStack.EMPTY, BlockHitResult.createMissed(context.getPlayer().getPos(), offset, pos));
					}

					boolean canPlace = state.canPlaceAt(context.getWorld(), pos)
							&& context.getWorld().canPlayerModifyAt(context.getPlayer(), pos)
							&& context.getWorld().getBlockState(pos).canReplace(placementContext)
							&& context.getWorld().canPlace(state, pos, ShapeContext.of(context.getPlayer()));
					if (canPlace) {
						return new Crosshair(InteractionType.PLACE_BLOCK);
					}
				} else { // carrying entity
					return new Crosshair(InteractionType.PLACE_ENTITY);
				}
			}
		} else {
			if (canCarryGeneral(context.getPlayer(), Vec3d.ofCenter(pos))) {
				BlockEntity blockEntity = context.getBlockEntity();
				BlockState state = context.getBlockState();

				if (ListHandler.isPermitted(state.getBlock())
						&& (state.getHardness(context.getWorld(), pos) != -1.0F || context.getPlayer().isCreative())
						&& (blockEntity != null || Constants.COMMON_CONFIG.settings.pickupAllBlocks)
				) {
					return new Crosshair(InteractionType.PICK_UP_BLOCK);
				}
			}
		}
		return null;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();
		CarryOnData carry = CarryOnDataManager.getCarryData(context.getPlayer());

		if (!carry.isCarrying()
				&& canCarryGeneral(context.getPlayer(), entity.getPos())
				&& entity.timeUntilRegen == 0
		) {
			if (entity instanceof TameableEntity tame) {
				UUID owner = tame.getOwnerUuid();
				UUID playerID = context.getPlayer().getGameProfile().getId();
				if (owner != null && !owner.equals(playerID)) {
					return null;
				}
			}

			if (!ListHandler.isPermitted(entity)) {
				if (!(entity instanceof PassiveEntity ageableMob && Constants.COMMON_CONFIG.settings.allowBabies && (ageableMob.getBreedingAge() < 0 || ageableMob.isBaby()))) {
					return null;
				}
			}

			if (!context.getPlayer().isCreative()) {
				if (!Constants.COMMON_CONFIG.settings.pickupHostileMobs && entity.getType().getSpawnGroup() == SpawnGroup.MONSTER) {
					return null;
				}

				if (Constants.COMMON_CONFIG.settings.maxEntityHeight < (double) entity.getHeight() || Constants.COMMON_CONFIG.settings.maxEntityWidth < (double) entity.getWidth()) {
					return null;
				}
			}

			return new Crosshair(InteractionType.PICK_UP_ENTITY);
		}

		return null;
	}
	//?}
}
