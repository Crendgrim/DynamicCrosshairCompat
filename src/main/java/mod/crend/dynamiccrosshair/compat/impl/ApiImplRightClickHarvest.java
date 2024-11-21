package mod.crend.dynamiccrosshair.compat.impl;

import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
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
	public Crosshair computeFromBlock(CrosshairContext context) {
		if (context.getItem() instanceof HoeItem) {
			BlockState blockState = context.getBlockState();
			if (blockState.getBlock() instanceof CropBlock cropBlock && cropBlock.isMature(blockState)) {
				return new Crosshair(InteractionType.USE_ITEM_ON_BLOCK);
			}
		}
		return null;
	}
}
