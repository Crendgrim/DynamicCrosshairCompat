package mod.crend.dynamiccrosshair.compat.farmersdelight;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import vectorwing.farmersdelight.FarmersDelight;

public class ApiImplFarmersDelight implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return FarmersDelight.MODID;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return FarmersDelightHandler.isAlwaysInteractableBlock(blockState);
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return FarmersDelightHandler.isInteractableBlock(blockState);
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		return FarmersDelightHandler.computeFromBlock(context);
	}
}
