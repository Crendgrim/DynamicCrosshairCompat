package mod.crend.dynamiccrosshair.compat.rightclickharvest;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.item.HoeItem;

public class ApiImplRightClickHarvest implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "rightclickharvest";
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		if (context.getItem() instanceof HoeItem) {
			BlockState blockState = context.getBlockState();
			if (blockState.getBlock() instanceof CropBlock cropBlock && cropBlock.isMature(blockState)) {
				return Crosshair.USE_ITEM;
			}
		}
		return null;
	}
}
