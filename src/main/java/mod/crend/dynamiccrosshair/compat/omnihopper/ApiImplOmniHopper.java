package mod.crend.dynamiccrosshair.compat.omnihopper;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import nl.enjarai.omnihopper.OmniHopper;
import nl.enjarai.omnihopper.blocks.HopperBlock;

public class ApiImplOmniHopper implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return OmniHopper.MODID;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		if (context.getBlock() instanceof HopperBlock) {
			return Crosshair.INTERACTABLE;
		}
		return null;
	}
}
