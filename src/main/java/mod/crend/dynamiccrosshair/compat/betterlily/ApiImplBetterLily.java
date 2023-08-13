package mod.crend.dynamiccrosshair.compat.betterlily;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Blocks;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PlaceableOnWaterItem;

public class ApiImplBetterLily implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "betterlily";
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		if (context.getBlock() instanceof LilyPadBlock) {
			ItemStack itemStack = context.getItemStack();
			if (!itemStack.isEmpty()
					&& !(itemStack.getItem() instanceof PlaceableOnWaterItem)
					&& context.world.getBlockState(context.getBlockPos().down()).isOf(Blocks.WATER)
			) {
				return Crosshair.HOLDING_BLOCK;
			}
		}

		return null;
	}
}
