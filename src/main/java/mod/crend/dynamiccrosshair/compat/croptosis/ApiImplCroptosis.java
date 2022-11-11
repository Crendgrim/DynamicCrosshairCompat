package mod.crend.dynamiccrosshair.compat.croptosis;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import wraith.croptosis.Croptosis;
import wraith.croptosis.item.FertilizerItem;
import wraith.croptosis.item.WateringCanItem;

public class ApiImplCroptosis implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Croptosis.MOD_ID;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (item instanceof WateringCanItem || item instanceof FertilizerItem);
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (!context.includeUsableItem()) return null;

		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof WateringCanItem) {
			if (WateringCanItem.isFilled(itemStack)) {
				return Crosshair.USABLE;
			}

			BlockHitResult blockHitResult = context.raycastWithFluid();
			if (context.world.getFluidState(blockHitResult.getBlockPos()).isIn(FluidTags.WATER)) {
				return Crosshair.USABLE;
			}
		}

		if (context.isWithBlock() && item instanceof FertilizerItem) {
			BlockState blockState = context.getBlockState();
			if (blockState.isOf(Blocks.DIRT) || blockState.isOf(Blocks.SAND) || blockState.isOf(Blocks.FARMLAND)) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
