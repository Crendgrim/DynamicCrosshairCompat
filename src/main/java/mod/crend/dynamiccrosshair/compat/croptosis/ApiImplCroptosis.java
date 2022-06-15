package mod.crend.dynamiccrosshair.compat.croptosis;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IUsableItemHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.mixin.IItemMixin;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import wraith.croptosis.Croptosis;
import wraith.croptosis.item.FertilizerItem;
import wraith.croptosis.item.WateringCanItem;

public class ApiImplCroptosis implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Croptosis.MOD_ID;
	}

	IUsableItemHandler usableItemHandler = new CroptosisUsableItemHandler();

	@Override
	public IUsableItemHandler getUsableItemHandler() {
		return usableItemHandler;
	}

	private static class CroptosisUsableItemHandler implements IUsableItemHandler {
		@Override
		public boolean isUsableItem(ItemStack itemStack) {
			Item item = itemStack.getItem();
			return (item instanceof WateringCanItem || item instanceof FertilizerItem);
		}

		@Override
		public Crosshair checkUsableItem(ClientPlayerEntity player, ItemStack itemStack) {
			Item item = itemStack.getItem();

			if (item instanceof WateringCanItem) {
				if (WateringCanItem.isFilled(itemStack)) {
					return Crosshair.USE_ITEM;
				}

				BlockHitResult blockHitResult = IItemMixin.invokeRaycast(MinecraftClient.getInstance().world, MinecraftClient.getInstance().player, RaycastContext.FluidHandling.ANY);
				if (MinecraftClient.getInstance().world.getFluidState(blockHitResult.getBlockPos()).isIn(FluidTags.WATER)) {
					return Crosshair.USE_ITEM;
				}
			}

			return null;
		}

		@Override
		public Crosshair checkUsableItemOnBlock(ClientPlayerEntity player, ItemStack itemStack, BlockPos blockPos, BlockState blockState) {
			Item item = itemStack.getItem();

			if (item instanceof FertilizerItem) {
				if (blockState.isOf(Blocks.DIRT) || blockState.isOf(Blocks.SAND) || blockState.isOf(Blocks.FARMLAND)) {
					return Crosshair.USE_ITEM;
				}
			}

			return null;
		}
	}
}
