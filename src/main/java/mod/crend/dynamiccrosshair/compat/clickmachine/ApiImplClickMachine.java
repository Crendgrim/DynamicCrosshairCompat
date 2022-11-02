package mod.crend.dynamiccrosshair.compat.clickmachine;

import com.kenza.clickmachine.blocks.AutoClickerBlock;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;

public class ApiImplClickMachine implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "clickmachine";
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		if (context.getBlock() instanceof AutoClickerBlock) {
			return Crosshair.INTERACTABLE;
		}

		return null;
	}
}
