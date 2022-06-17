package mod.crend.dynamiccrosshair.compat.early_buckets;

import dev.satyrn.early_buckets.BucketMod;
import dev.satyrn.early_buckets.item.CustomBucketItem;
import dev.satyrn.early_buckets.item.CustomEntityBucketItem;
import dev.satyrn.early_buckets.item.CustomPowderSnowBucketItem;
import mod.crend.dynamiccrosshair.DynamicCrosshair;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.mixin.early_buckets.ICustomPowderSnowBucketItemMixin;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.component.CrosshairVariant;
import mod.crend.dynamiccrosshair.component.ModifierUse;
import mod.crend.dynamiccrosshair.config.BlockCrosshairPolicy;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.hit.BlockHitResult;

public class ApiImplEarlyGameBuckets implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return BucketMod.MOD_ID;
	}

	@Override
	public Crosshair checkBlockItem(CrosshairContext context) {
		Item handItem = context.getItem();
		if (handItem instanceof CustomPowderSnowBucketItem powderSnowBucketItem) {
			if (DynamicCrosshair.config.dynamicCrosshairHoldingBlock() == BlockCrosshairPolicy.IfInteractable) {
				if (context.isWithBlock()) {
					ItemPlacementContext itemPlacementContext = new ItemPlacementContext(context.player, context.player.getActiveHand(), context.getItemStack(), (BlockHitResult) context.hitResult);
					BlockState placementState = powderSnowBucketItem.getBlock().getPlacementState(itemPlacementContext);
					if (placementState != null && ((ICustomPowderSnowBucketItemMixin) powderSnowBucketItem).invokeCanPlace(itemPlacementContext, placementState)) return new Crosshair(CrosshairVariant.HoldingBlock);
					return Crosshair.NONE;
				}
			} else return Crosshair.HOLDING_BLOCK;
		}

		return null;
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		if (context.isWithBlock()) {

			Item handItem = context.getItem();
			Block block = context.getBlock();

			if (handItem instanceof CustomEntityBucketItem) {
				if (DynamicCrosshair.config.dynamicCrosshairHoldingBlock() != BlockCrosshairPolicy.Disabled) {
					return new Crosshair(CrosshairVariant.HoldingBlock, ModifierUse.USE_ITEM);
				}
				return Crosshair.USE_ITEM;
			}
			if (block instanceof AbstractCauldronBlock cauldron && !context.player.shouldCancelInteraction()) {
				if (handItem instanceof CustomBucketItem bucketItem) {
					Fluid fluid = bucketItem.getFluid();
					if (fluid == Fluids.WATER || fluid == Fluids.LAVA) {
						return Crosshair.USE_ITEM;
					}
					if (fluid == Fluids.EMPTY && cauldron.isFull(context.getBlockState())) {
						return Crosshair.USE_ITEM;
					}
				}
				if (handItem instanceof CustomPowderSnowBucketItem) {
					return Crosshair.USE_ITEM;
				}
			}
			if (handItem instanceof CustomBucketItem bucketItem) {
				Fluid fluid = bucketItem.getFluid();
				if (fluid != Fluids.EMPTY) {
					if (DynamicCrosshair.config.dynamicCrosshairHoldingBlock() != BlockCrosshairPolicy.Disabled) {
						return Crosshair.HOLDING_BLOCK;
					}
					return Crosshair.USE_ITEM;
				}
				else if (block.equals(Blocks.POWDER_SNOW)) return Crosshair.USE_ITEM;
			}

		} else if (!context.isTargeting()) {

			if (context.getItem() instanceof CustomEntityBucketItem) {
				return new Crosshair(CrosshairVariant.HoldingBlock, ModifierUse.USE_ITEM);
			}

		}
		return null;
	}
}