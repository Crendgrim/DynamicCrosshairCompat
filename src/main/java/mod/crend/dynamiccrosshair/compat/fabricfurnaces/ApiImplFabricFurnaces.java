package mod.crend.dynamiccrosshair.compat.fabricfurnaces;

import draylar.fabricfurnaces.block.FabricFurnaceBlock;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;

public class ApiImplFabricFurnaces implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "fabric-furnaces";
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof FabricFurnaceBlock;
	}
}
