package mod.crend.dynamiccrosshair.compat.dehydration;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.mixin.IItemMixin;
import net.dehydration.access.ThirstManagerAccess;
import net.dehydration.block.*;
import net.dehydration.block.entity.BambooPumpEntity;
import net.dehydration.init.BlockInit;
import net.dehydration.init.ConfigInit;
import net.dehydration.init.ItemInit;
import net.dehydration.item.HandbookItem;
import net.dehydration.item.LeatherFlask;
import net.dehydration.item.PurifiedBucket;
import net.dehydration.thirst.ThirstManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;

public class ApiImplDehydration implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "dehydration";
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof HandbookItem;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof LeatherFlask flask) {
			NbtCompound tags = itemStack.getNbt();
			if (context.isWithBlock()) {
				BlockState blockState = context.getBlockState();
				Block block = blockState.getBlock();
				if (block instanceof LeveledCauldronBlock
						|| block instanceof CauldronBlock
						|| block instanceof CopperCauldronBlock
						|| block instanceof CopperLeveledCauldronBlock
						|| block instanceof CampfireCauldronBlock) {
					if (context.player.isSneaking()) {
						if (itemStack.hasNbt() && tags.getInt("leather_flask") > 0 || !itemStack.hasNbt()) {
							if (block instanceof LeveledCauldronBlock leveledCauldronBlock) {
								if (leveledCauldronBlock.isFull(blockState)) {
									return null;
								}
							} else if (block instanceof CopperLeveledCauldronBlock leveledCauldronBlock) {
								if (leveledCauldronBlock.isFull(blockState)) {
									return null;
								}
							} else if (block instanceof CampfireCauldronBlock cauldron){
								if (cauldron.isFull(blockState)) {
									return null;
								}
							}
							return Crosshair.USABLE;
						}
					} else if (block instanceof LeveledCauldronBlock && blockState.get(LeveledCauldronBlock.LEVEL) > 0 && itemStack.hasNbt() && tags.getInt("leather_flask") < 2 + flask.addition) {
						return Crosshair.USABLE;
					}
				}
			} else {
				BlockHitResult hitResult = IItemMixin.invokeRaycast(context.world, context.player, RaycastContext.FluidHandling.SOURCE_ONLY);
				BlockPos blockPos = hitResult.getBlockPos();
				if (hitResult.getType() == HitResult.Type.BLOCK && context.world.canPlayerModifyAt(context.player, blockPos) && context.world.getFluidState(blockPos).isIn(FluidTags.WATER) && itemStack.hasNbt()) {
					if (context.player.isSneaking() && tags.getInt("leather_flask") != 0) {
						return Crosshair.USABLE;
					}

					if (tags.getInt("leather_flask") < 2 + flask.addition) {
						return Crosshair.USABLE;
					}
				}
			}
		}

		if (item instanceof PurifiedBucket) {
			BlockHitResult blockHitResult = IItemMixin.invokeRaycast(context.world, context.player, RaycastContext.FluidHandling.NONE);
			if (blockHitResult.getType() == HitResult.Type.BLOCK) {
				BlockPos blockPos = blockHitResult.getBlockPos();
				Direction direction = blockHitResult.getSide();
				BlockPos blockPos2 = blockPos.offset(direction);
				if (context.world.canPlayerModifyAt(context.player, blockPos) && context.player.canPlaceOn(blockPos2, direction, itemStack)) {
					return Crosshair.HOLDING_BLOCK;
				}
			}
		}

		return null;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		if (context.player.isSneaking() && context.isMainHand() && context.getItemStack().isEmpty()) {
			HitResult hitResult = context.player.raycast(1.5, 0.0F, true);
			BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
			if (context.world.getFluidState(blockPos).isIn(FluidTags.WATER) && (context.world.getFluidState(blockPos).isStill() || ConfigInit.CONFIG.allow_non_flowing_water_sip)) {
				ThirstManager thirstManager = ((ThirstManagerAccess) context.player).getThirstManager();
				if (thirstManager.isNotFull()) {
					return Crosshair.INTERACTABLE;
				}
			}
		}

		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();

		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (block instanceof BambooPumpBlock && context.getBlockEntity() instanceof BambooPumpEntity bambooPumpEntity) {
			ItemStack pumpStack = bambooPumpEntity.getStack(0);
			if (pumpStack.isEmpty()) {
				if (itemStack.isOf(Items.BUCKET) || itemStack.isOf(Items.GLASS_BOTTLE) || itemStack.getItem() instanceof LeatherFlask && !LeatherFlask.isFlaskFull(itemStack)) {
					return Crosshair.USABLE;
				}
			} else {
				if (itemStack.isEmpty() && context.player.isSneaking()) {
					return Crosshair.INTERACTABLE;
				}

				if (pumpStack.isOf(Items.BUCKET) || pumpStack.isOf(Items.GLASS_BOTTLE) || pumpStack.getItem() instanceof LeatherFlask && !LeatherFlask.isFlaskFull(pumpStack)) {
					if (ConfigInit.CONFIG.pump_cooldown == 0 || bambooPumpEntity.getCooldown() <= 0) {
						return Crosshair.INTERACTABLE;
					}
				}
			}
		}

		if (block instanceof CampfireCauldronBlock) {
			int level = blockState.get(CampfireCauldronBlock.LEVEL);
			if (level < 4) {
				if (item == Items.WATER_BUCKET) {
					return Crosshair.USABLE;
				}
				if (item == Items.POTION && (PotionUtil.getPotion(itemStack) == Potions.WATER || PotionUtil.getPotion(itemStack) == ItemInit.PURIFIED_WATER)) {
					return Crosshair.USABLE;
				}
			}
			if (level == 4) {
				if (item == Items.BUCKET) {
					return Crosshair.USABLE;
				}
			}
			if (level > 0) {
				if (item == Items.GLASS_BOTTLE || item instanceof LeatherFlask) {
					return Crosshair.USABLE;
				}
			}
		}

		if (block instanceof AbstractCopperCauldronBlock) {
			if (item == Items.WATER_BUCKET || item == Items.POWDER_SNOW_BUCKET) {
				return Crosshair.USABLE;
			}
			if (block instanceof CopperLeveledCauldronBlock) {
				if (block == BlockInit.COPPER_POWDERED_CAULDRON_BLOCK || block == BlockInit.COPPER_WATER_CAULDRON_BLOCK) {
					if (item == Items.BUCKET && blockState.get(CopperLeveledCauldronBlock.LEVEL) == 3) {
						return Crosshair.USABLE;
					}
				}
				if (block == BlockInit.COPPER_PURIFIED_WATER_CAULDRON_BLOCK || block == BlockInit.COPPER_WATER_CAULDRON_BLOCK) {
					if (item == Items.GLASS_BOTTLE) {
						return Crosshair.USABLE;
					}
				}
			}
		}

		return null;
	}
}