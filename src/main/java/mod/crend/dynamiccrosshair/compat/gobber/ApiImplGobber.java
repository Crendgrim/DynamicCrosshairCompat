package mod.crend.dynamiccrosshair.compat.gobber;

import com.kwpugh.gobber2.Gobber2;
import com.kwpugh.gobber2.items.medallions.MedallionExp;
import com.kwpugh.gobber2.items.other.SpecialItem;
import com.kwpugh.gobber2.items.rings.*;
import com.kwpugh.gobber2.items.staffs.*;
import com.kwpugh.gobber2.util.ExpUtils;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ApiImplGobber implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Gobber2.MOD_ID;
	}

	@Override
	public Crosshair checkEntity(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();
		Entity entity = context.getEntity();

		if ((item instanceof StaffEnsnarement || item instanceof StaffHostileEnsnarement) && itemStack.getOrCreateSubNbt("captured_entity").isEmpty()) {
			if (entity instanceof AnimalEntity
					|| entity instanceof MerchantEntity
					|| entity instanceof GolemEntity
					|| entity instanceof SquidEntity
					|| entity instanceof FishEntity
					|| entity instanceof DolphinEntity
					|| entity instanceof AllayEntity
					|| entity instanceof BatEntity
			) {
				return Crosshair.USE_ITEM;
			}
			if ((Gobber2.CONFIG.GENERAL.staffEnsnarementHotileMobs || item instanceof StaffHostileEnsnarement)
					&& entity instanceof HostileEntity && !(entity instanceof WitherEntity)) {
				return Crosshair.USE_ITEM;
			}
			if (item instanceof StaffHostileEnsnarement && (entity instanceof GhastEntity || entity instanceof PhantomEntity)) {
				return Crosshair.USE_ITEM;
			}
		}

		return null;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (   item instanceof RingBlink
				|| item instanceof RingCuring
				|| item instanceof RingEnderchest
				|| item instanceof RingHaste
				|| item instanceof RingLuck
				|| item instanceof RingSunshine
				|| item instanceof RingSwiftness
				|| item instanceof RingTraveler
				|| item instanceof RingVision
				|| item instanceof StaffClearing
				|| item instanceof StaffFarmer
		);
	}

	@Override
	public Crosshair checkRangedWeapon(CrosshairContext context) {
		Item item = context.getItem();
		if (item instanceof StaffSniper) {
			return Crosshair.RANGED_WEAPON;
		}

		return null;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (item instanceof MedallionExp
				|| item instanceof RingAbove
				|| item instanceof RingAirWalking
				|| item instanceof RingAscent
				|| item instanceof RingAttraction
				|| item instanceof RingExplorer
				|| item instanceof RingReturn
				|| item instanceof RingVoid
				|| item instanceof RingMiner
				|| item instanceof RingTeleport
				|| item instanceof StaffEnsnarement
				|| item instanceof StaffHostileEnsnarement
				|| item instanceof StaffNature
				|| item instanceof StaffStars
				|| item instanceof StaffTransformation
				|| item instanceof SpecialItem);
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();
		if (item instanceof MedallionExp medallion) {
			if (context.player.isSneaking() && medallion.getXPStored(itemStack) != 1425) {
				int playerXP = ExpUtils.getPlayerXP(context.player);
				if (playerXP > 0) {
					return Crosshair.USE_ITEM;
				}
			}
			if (!context.player.isSneaking() && medallion.getXPStored(itemStack) != 0) {
				return Crosshair.USE_ITEM;
			}
		}
		if (item instanceof BaseRing) {
			if (item instanceof RingAbove) {
				if (context.world.getRegistryKey() == World.OVERWORLD) {
					return Crosshair.USE_ITEM;
				}
			} else if (item instanceof RingAirWalking) {
				if (Gobber2.CONFIG.GENERAL.enableAirWalkingPlaceGlass) {
					return Crosshair.USE_ITEM;
				}
			} else if (item instanceof RingAscent) {
				if (context.isMainHand()) {
					if (context.player.isSneaking()) {
						return Crosshair.USE_ITEM;
					}
				} else {
					if (context.player.isOnGround()) {
						return Crosshair.USE_ITEM;
					}
				}
			} else if (item instanceof RingAttraction
					|| item instanceof RingExplorer
					|| item instanceof RingReturn
					|| item instanceof RingVoid
			) {
				if (!context.player.isSneaking()) {
					return Crosshair.USE_ITEM;
				}
			} else if (item instanceof RingMiner) {
				if (context.player.isSneaking()) {
					return Crosshair.USE_ITEM;
				}
			} else if (item instanceof RingTeleport) {
				boolean hasPosition = RingTeleport.getPosition(itemStack) != null;
				if (context.isWithBlock()) {
					if (context.player.isSneaking() && !hasPosition) {
						return Crosshair.USE_ITEM;
					}
				} else {
					if (hasPosition) {
						return Crosshair.USE_ITEM;
					}
				}
			}
		}
		if (item instanceof BaseStaff) {
			if (item instanceof StaffEnsnarement || item instanceof StaffHostileEnsnarement) {
				if (context.isWithBlock() && itemStack.hasNbt() && itemStack.getNbt().contains("captured_entity")) {
					return Crosshair.USE_ITEM;
				}
			} else if (item instanceof StaffNature) {
				if (context.isWithBlock()) {
					Block block = context.getBlock();
					if (context.player.isSneaking()) {
						if (block == Blocks.GLASS) {
							return Crosshair.USE_ITEM;
						}
					} else {
						if (block == Blocks.ACACIA_SAPLING
								|| block == Blocks.BIRCH_SAPLING
								|| block == Blocks.DARK_OAK_SAPLING
								|| block == Blocks.JUNGLE_SAPLING
								|| block == Blocks.OAK_SAPLING
								|| block == Blocks.SPRUCE_SAPLING
								|| block == Blocks.AZALEA
								|| block == Blocks.FLOWERING_AZALEA
								|| block == Blocks.MANGROVE_PROPAGULE
								|| block == Blocks.SUGAR_CANE
								|| block == Blocks.BAMBOO_SAPLING
								|| block == Blocks.CACTUS
						) {
							return Crosshair.USE_ITEM;
						}
					}
				}
			} else if (item instanceof StaffStars) {
				if (context.isWithBlock()) {
					BlockState blockState = context.getBlockState();
					BlockPos pos = context.getBlockPos();
					if (blockState.isSolidBlock(context.world, context.getBlockPos())) {
						BlockPos torchPos;
						switch (context.getBlockHitSide()) {
							case UP:
								torchPos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
								break;
							case NORTH:
								torchPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1);
								break;
							case SOUTH:
								torchPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1);
								break;
							case WEST:
								torchPos = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ());
								break;
							case EAST:
								torchPos = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ());
								break;
							default:
								return null;
						}

						if (context.world.getBlockState(torchPos).isAir() || context.world.getBlockState(torchPos).getFluidState().isStill()) {
							return Crosshair.HOLDING_BLOCK;
						}
					}
				}
			} else if (item instanceof StaffTransformation) {
				if (context.isMainHand() && context.getItemStack(Hand.OFF_HAND).isOf(Items.BOOK) && itemStack.getDamage() < itemStack.getMaxDamage() - 1) {
					return Crosshair.USE_ITEM;
				}
			}
		}
		if (item instanceof SpecialItem) {
			if (!context.player.isSneaking()) {
				return Crosshair.USE_ITEM;
			}
		}

		return null;
	}
}
