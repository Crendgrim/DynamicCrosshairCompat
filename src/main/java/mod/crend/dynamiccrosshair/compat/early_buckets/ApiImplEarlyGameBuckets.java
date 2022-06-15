package mod.crend.dynamiccrosshair.compat.early_buckets;

import dev.satyrn.early_buckets.BucketMod;
import dev.satyrn.early_buckets.item.CustomBucketItem;
import dev.satyrn.early_buckets.item.CustomEntityBucketItem;
import dev.satyrn.early_buckets.item.CustomPowderSnowBucketItem;
import mod.crend.dynamiccrosshair.DynamicCrosshair;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IBlockItemHandler;
import mod.crend.dynamiccrosshair.api.IUsableItemHandler;
import mod.crend.dynamiccrosshair.compat.mixin.early_buckets.ICustomPowderSnowBucketItemMixin;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.component.ModifierUse;
import mod.crend.dynamiccrosshair.component.Style;
import mod.crend.dynamiccrosshair.config.BlockCrosshairPolicy;
import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class ApiImplEarlyGameBuckets implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return BucketMod.MOD_ID;
	}

	IUsableItemHandler usableItemHandler = new EarlyGameBucketsUsableItemHandler();

	@Override
	public IUsableItemHandler getUsableItemHandler() {
		return usableItemHandler;
	}

	@Override
	public IBlockItemHandler getBlockItemHandler() {
		return (player, itemStack) -> {
			Item handItem = itemStack.getItem();
			if (handItem instanceof CustomPowderSnowBucketItem powderSnowBucketItem) {
				if (DynamicCrosshair.config.dynamicCrosshairHoldingBlock() == BlockCrosshairPolicy.IfInteractable) {
					HitResult hitResult = MinecraftClient.getInstance().crosshairTarget;
					if (hitResult.getType() == HitResult.Type.BLOCK) {
						ItemPlacementContext itemPlacementContext = new ItemPlacementContext(player, player.getActiveHand(), itemStack, (BlockHitResult) hitResult);
						BlockState placementState = powderSnowBucketItem.getBlock().getPlacementState(itemPlacementContext);
						if (placementState != null && ((ICustomPowderSnowBucketItemMixin) powderSnowBucketItem).invokeCanPlace(itemPlacementContext, placementState)) return new Crosshair(Style.HoldingBlock);
						return Crosshair.NONE;
					}
				} else return Crosshair.HOLDING_BLOCK;
			}

			return null;
		};
	}

	private static class EarlyGameBucketsUsableItemHandler implements IUsableItemHandler {
		@Override
		public Crosshair checkUsableItemOnBlock(ClientPlayerEntity player, ItemStack itemStack, BlockPos blockPos, BlockState blockState) {
			Item handItem = itemStack.getItem();
			Block block = blockState.getBlock();

			if (handItem instanceof CustomEntityBucketItem) {
				if (DynamicCrosshair.config.dynamicCrosshairHoldingBlock() != BlockCrosshairPolicy.Disabled) {
					return new Crosshair(Style.HoldingBlock, ModifierUse.USE_ITEM);
				}
				return Crosshair.USE_ITEM;
			}
			if (block instanceof AbstractCauldronBlock && !player.shouldCancelInteraction()) {
				if (handItem instanceof CustomBucketItem bucketItem) {
					Fluid fluid = bucketItem.getFluid();
					if (fluid == Fluids.WATER || fluid == Fluids.LAVA) {
						return Crosshair.USE_ITEM;
					}
					if (fluid == Fluids.EMPTY && ((AbstractCauldronBlock) block).isFull(blockState)) {
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

			return null;
		}

		@Override
		public Crosshair checkUsableItemOnMiss(ClientPlayerEntity player, ItemStack itemStack) {
			if (itemStack.getItem() instanceof CustomEntityBucketItem) {
				return new Crosshair(Style.HoldingBlock, ModifierUse.USE_ITEM);
			}

			return null;
		}
	}
}