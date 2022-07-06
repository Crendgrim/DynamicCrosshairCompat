package mod.crend.dynamiccrosshair.compat.voidz;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.voidz.block.PortalBlock;

public class ApiImplVoidZ implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "voidz";
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		Block block = context.getBlock();

		if (block instanceof PortalBlock) {
			return Crosshair.INTERACTABLE;
		}

		return null;
	}
}
