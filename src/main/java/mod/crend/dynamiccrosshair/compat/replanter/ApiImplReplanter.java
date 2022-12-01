package mod.crend.dynamiccrosshair.compat.replanter;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class ApiImplReplanter implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "replanter";
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack itemStack = context.getItemStack();

		if (itemStack.getItem() instanceof BlockItem blockItem) {
			if (block instanceof CropBlock) {
				if (blockItem.getBlock() instanceof CropBlock) {
					return Crosshair.USABLE;
				}
			} else if (block instanceof NetherWartBlock && block == blockItem.getBlock()) {
				if (blockState.get(NetherWartBlock.AGE) == 3) {
					return Crosshair.USABLE;
				}
			} else if (block instanceof CocoaBlock) {
				if (blockState.get(CocoaBlock.AGE) == CocoaBlock.MAX_AGE) {
					return Crosshair.USABLE;
				}
			}
		}

		return null;
	}
}
