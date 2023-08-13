package mod.crend.dynamiccrosshair.compat.bewitchment;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.compat.mixin.bewitchment.BWTameableEntityAccessor;
import mod.crend.dynamiccrosshair.compat.mixin.bewitchment.SaltLineBlockAccessor;
import mod.crend.dynamiccrosshair.component.Crosshair;
import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.api.entity.Pledgeable;
import moriyashiine.bewitchment.common.block.*;
import moriyashiine.bewitchment.common.block.entity.LockableBlockEntity;
import moriyashiine.bewitchment.common.block.entity.interfaces.Lockable;
import moriyashiine.bewitchment.common.block.entity.interfaces.SigilHolder;
import moriyashiine.bewitchment.common.block.entity.interfaces.TaglockHolder;
import moriyashiine.bewitchment.common.entity.DemonMerchant;
import moriyashiine.bewitchment.common.entity.living.BaphometEntity;
import moriyashiine.bewitchment.common.entity.living.HerneEntity;
import moriyashiine.bewitchment.common.entity.living.LeonardEntity;
import moriyashiine.bewitchment.common.entity.living.LilithEntity;
import moriyashiine.bewitchment.common.entity.living.util.BWHostileEntity;
import moriyashiine.bewitchment.common.entity.living.util.BWTameableEntity;
import moriyashiine.bewitchment.common.item.AthameItem;
import moriyashiine.bewitchment.common.item.ChalkItem;
import moriyashiine.bewitchment.common.item.TaglockItem;
import moriyashiine.bewitchment.common.item.WaystoneItem;
import moriyashiine.bewitchment.common.item.util.BWBookItem;
import moriyashiine.bewitchment.common.misc.BWUtil;
import moriyashiine.bewitchment.common.recipe.AthameStrippingRecipe;
import moriyashiine.bewitchment.common.registry.BWObjects;
import moriyashiine.bewitchment.common.registry.BWProperties;
import moriyashiine.bewitchment.common.registry.BWRecipeTypes;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;

public class BewitchmentHandler {
	public static boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof BWBookItem || item instanceof WaystoneItem;
	}

	public static Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof AthameItem && context.isWithBlock()) {
			BlockState blockState = context.getBlockState();
			AthameStrippingRecipe entry = context.world.getRecipeManager()
					.listAllOfType(BWRecipeTypes.ATHAME_STRIPPING_RECIPE_TYPE)
					.stream()
					.filter((recipe) -> recipe.log == blockState.getBlock())
					.findFirst()
					.orElse(null);
			if (entry != null) {
				return Crosshair.USABLE;
			} else {
				BlockEntity blockEntity;
				if (blockState.getBlock() instanceof DoorBlock && blockState.get(DoorBlock.HALF) == DoubleBlockHalf.UPPER) {
					blockEntity = context.world.getBlockEntity(context.getBlockPos().down());
				} else {
					blockEntity = context.getBlockEntity();
				}
				if (blockEntity instanceof SigilHolder sigilHolder) {
					if (context.player.getUuid().equals(sigilHolder.getOwner())) {
						return Crosshair.USABLE;
					}
				} else if (blockEntity instanceof TaglockHolder taglockHolder) {
					if (context.player.getUuid().equals(taglockHolder.getOwner())) {
						return Crosshair.USABLE;
					}
				} else if (blockEntity instanceof Lockable lockable) {
					if (context.player.getUuid().equals(lockable.getOwner()) && !lockable.getEntities().isEmpty()) {
						return Crosshair.USABLE;
					}
				}
			}
		}

		if (item instanceof ChalkItem && context.isWithBlock()) {
			ItemPlacementContext placementContext = new ItemPlacementContext(context.world, context.player, context.getHand(), itemStack, context.getBlockHitResult());
			BlockPos pos = context.getBlockPos();
			if (!context.world.getBlockState(pos).canReplace(placementContext)) {
				pos = pos.offset(context.getBlockHitSide());
			}

			if (context.world.getBlockState(pos).canReplace(placementContext)) {
				return Crosshair.HOLDING_BLOCK;
			}
		}

		if (item instanceof TaglockItem) {
			if (context.isWithBlock()) {
				BlockState blockState = context.getBlockState();
				if (blockState.getBlock() instanceof BedBlock) {
					if (context.player.isSneaking()) {
						return Crosshair.USABLE;
					}
				} else {
					ItemStack stack = context.getItemStack();
					BlockEntity blockEntity;
					if (blockState.getBlock() instanceof DoorBlock && blockState.get(DoorBlock.HALF) == DoubleBlockHalf.UPPER) {
						blockEntity = context.world.getBlockEntity(context.getBlockPos().down());
					} else {
						blockEntity = context.getBlockEntity();
					}
					if (blockEntity instanceof TaglockHolder taglockHolder) {
						if (context.player.getUuid().equals(taglockHolder.getOwner())) {
							int firstEmpty = taglockHolder.getFirstEmptySlot();
							if (firstEmpty != -1) {
								return Crosshair.USABLE;
							}
						}
					} else {
						UUID uuid;
						if (blockEntity instanceof Lockable lockable) {
							if (TaglockItem.hasTaglock(stack) && context.player.getUuid().equals(lockable.getOwner()) && lockable.getLocked()) {
								uuid = TaglockItem.getTaglockUUID(stack);
								if (!lockable.getEntities().contains(uuid)) {
									return Crosshair.USABLE;
								}
							}
						} else if (blockEntity instanceof SigilHolder) {
							if (TaglockItem.hasTaglock(stack)) {
								return Crosshair.USABLE;
							}
						}
					}
				}
			}
		}

		return null;
	}

	public static boolean isAlwaysInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof CrystalBallBlock || block == BWObjects.GOLDEN_GLYPH;
	}

	public static boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof BrazierBlock || block instanceof SaltLineBlock || block instanceof WitchCauldronBlock;
	}

	public static Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack itemStack = context.getItemStack();

		if (context.getBlockEntity() instanceof LockableBlockEntity lockable) {
			if (!lockable.test(context.player)) {
				return Crosshair.NONE.withFlag(Crosshair.Flag.FixedModifierUse);
			}
		}

		if (block instanceof BrambleBlock.Fruiting) {
			if (blockState.get(BWProperties.HAS_FRUIT)) {
				return Crosshair.INTERACTABLE;
			} else if (itemStack.getItem() instanceof BoneMealItem) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof BrazierBlock) {
			if (!blockState.get(Properties.WATERLOGGED)) {
				if (blockState.get(Properties.LIT)) {
					return Crosshair.INTERACTABLE;
				} else if (!itemStack.isEmpty()) {
					return Crosshair.USABLE;
				} else {
					return Crosshair.INTERACTABLE;
				}
			}
		}

		if (block instanceof SaltLineBlock saltLineBlock) {
			if (context.player.getAbilities().allowModifyWorld
					&& (SaltLineBlockAccessor.invokeIsFullyConnected(blockState) || SaltLineBlockAccessor.invokeIsNotConnected(blockState))) {
				BlockState placementState = ((SaltLineBlockAccessor) saltLineBlock).invokeGetPlacementState(context.world, context.getBlockPos());
				if (placementState != blockState) {
					return Crosshair.INTERACTABLE;
				}
			}
		}

		if (block instanceof WitchCauldronBlock) {
			if ((itemStack.getItem() instanceof NameTagItem && itemStack.hasCustomName())
					|| itemStack.isOf(Items.BUCKET)
					|| itemStack.isOf(Items.WATER_BUCKET)
					|| itemStack.isOf(Items.GLASS_BOTTLE)
					|| (itemStack.isOf(Items.POTION) && PotionUtil.getPotion(itemStack) == Potions.WATER)
			) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}

	public static boolean isInteractableEntity(Entity entity) {
		return entity instanceof DemonMerchant || (entity instanceof Pledgeable && !(entity instanceof LeonardEntity));
	}

	public static Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();
		ItemStack itemStack = context.getItemStack();

		if (entity instanceof BWTameableEntity tameableEntity) {
			if (tameableEntity.isBreedingItem(itemStack)) {
				if (tameableEntity.getHealth() < tameableEntity.getMaxHealth()) {
					return Crosshair.USABLE;
				}
			} else if (!tameableEntity.isTamed()) {
				if (((BWTameableEntityAccessor) tameableEntity).invokeIsTamingItem(itemStack)) {
					return Crosshair.USABLE;
				}
			} else if (tameableEntity.isOwner(context.player)) {
				return Crosshair.INTERACTABLE;
			}
		}

		if (entity instanceof BWHostileEntity trader && entity instanceof DemonMerchant merchant) {
			if (entity instanceof BaphometEntity baphomet && !BewitchmentAPI.isPledged(context.player, baphomet.getPledgeID())) {
				return null;
			}
			if (trader.isAlive() && trader.getTarget() == null) {
				if (BWUtil.rejectTrades(trader)) {
					return null;
				}

				if (!merchant.getOffers().isEmpty()) {
					return Crosshair.INTERACTABLE;
				}
			}
		}

		if (entity instanceof HerneEntity herne) {
			if (herne.isAlive() && herne.getTarget() == null && BewitchmentAPI.isWerewolf(context.player, true)) {
				if (itemStack.isOf(BWObjects.ACONITE)) {
					return Crosshair.USABLE;
				}
			}
		}
		if (entity instanceof LilithEntity lilith) {
			if (lilith.isAlive() && lilith.getTarget() == null && BewitchmentAPI.isVampire(context.player, true)) {
				if (itemStack.isOf(BWObjects.GARLIC)) {
					return Crosshair.USABLE;
				}
			}
		}

		return null;
	}
}
