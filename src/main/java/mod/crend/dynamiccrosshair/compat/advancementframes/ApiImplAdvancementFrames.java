package mod.crend.dynamiccrosshair.compat.advancementframes;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.mehvahdjukaar.advframes.blocks.AdvancementFrameBlock;
import net.minecraft.block.Block;

public class ApiImplAdvancementFrames implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "advancementframes";
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		Block block = context.getBlock();
		if (block instanceof AdvancementFrameBlock) {
			return Crosshair.INTERACTABLE;
		}
		return null;
	}
}
