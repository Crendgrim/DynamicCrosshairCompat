package mod.crend.dynamiccrosshair.compat.fabricfurnaces;

import draylar.fabricfurnaces.block.FabricFurnaceBlock;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;

public class ApiImplFabricFurnaces implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "fabric-furnaces";
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		if (context.getBlock() instanceof FabricFurnaceBlock) {
			return Crosshair.INTERACTABLE;
		}

		return null;
	}
}
