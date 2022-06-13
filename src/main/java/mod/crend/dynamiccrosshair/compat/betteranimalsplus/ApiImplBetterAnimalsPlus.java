package mod.crend.dynamiccrosshair.compat.betteranimalsplus;

import dev.itsmeow.betteranimalsplus.common.block.BlockTurkey;
import dev.itsmeow.betteranimalsplus.common.item.ItemThrowableCustomEgg;
import dev.itsmeow.betteranimalsplus.imdlib.entity.interfaces.IBucketable;
import dev.itsmeow.betteranimalsplus.imdlib.item.ItemModFishBucket;
import mod.crend.dynamiccrosshair.DynamicCrosshair;
import mod.crend.dynamiccrosshair.api.*;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.component.ModifierUse;
import mod.crend.dynamiccrosshair.component.Style;
import mod.crend.dynamiccrosshair.config.BlockCrosshairPolicy;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class ApiImplBetterAnimalsPlus implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "betteranimalsplus";
	}

	@Override
	public IEntityHandler getEntityHandler() {
		return (player, itemStack, entity) -> {

			if (entity instanceof IBucketable) {
				return Crosshair.USE_ITEM;
			}

			return null;
		};
	}

	@Override
	public IThrowableItemHandler getThrowableItemHandler() {
		return (player, itemStack) -> {
			if (itemStack.getItem() instanceof ItemThrowableCustomEgg<?>) {
				return Crosshair.THROWABLE;
			}

			return null;
		};
	}

	@Override
	public IBlockInteractHandler getBlockInteractHandler() {
		return (player, itemStack, blockPos, blockState) -> {
			if (blockState.getBlock() instanceof BlockTurkey) {
				if (player.canConsume(false)) {
					return Crosshair.USE_ITEM;
				}
			}

			return null;
		};
	}

	IUsableItemHandler usableItemHandler = new AnimalsPlusUsableItemHandler();

	@Override
	public IUsableItemHandler getUsableItemHandler() {
		return usableItemHandler;
	}

	private static class AnimalsPlusUsableItemHandler implements IUsableItemHandler {
		@Override
		public Crosshair checkUsableItemOnBlock(ClientPlayerEntity player, ItemStack itemStack, BlockPos blockPos, BlockState blockState) {

			if (itemStack.getItem() instanceof ItemModFishBucket<?>) {
				if (DynamicCrosshair.config.dynamicCrosshairHoldingBlock() != BlockCrosshairPolicy.Disabled) {
					return new Crosshair(Style.HoldingBlock, ModifierUse.USE_ITEM);
				}
				return Crosshair.USE_ITEM;
			}

			return null;
		}

		@Override
		public Crosshair checkUsableItemOnMiss(ClientPlayerEntity player, ItemStack itemStack) {

			if (DynamicCrosshair.config.dynamicCrosshairHoldingBlock() == BlockCrosshairPolicy.Always) {
				if (itemStack.getItem() instanceof ItemModFishBucket<?>) {
					return new Crosshair(Style.HoldingBlock, ModifierUse.USE_ITEM);
				}
			}

			return null;
		}
	}
}
