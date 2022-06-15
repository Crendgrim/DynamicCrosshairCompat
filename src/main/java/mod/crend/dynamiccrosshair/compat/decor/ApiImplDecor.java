package mod.crend.dynamiccrosshair.compat.decor;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IBlockInteractHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.gmsgarcia.decor4fabric.blocks.*;
import net.gmsgarcia.decor4fabric.mainDecor;
import net.minecraft.block.Block;

public class ApiImplDecor implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return mainDecor.MOD_ID;
	}

	@Override
	public IBlockInteractHandler getBlockInteractHandler() {
		return context -> {
			Block block = context.getBlock();
			if (block instanceof workBench) {
				return Crosshair.INTERACTABLE;
			}
			if (!context.player.shouldCancelInteraction()) {
				if (context.getItemStack().isEmpty() && (
						   block instanceof logBench
						|| block instanceof logBench2
						|| block instanceof logBench3
						|| block instanceof logSmallStool
				)) {
					return Crosshair.INTERACTABLE;
				}
				if (block instanceof logChair) {
					return Crosshair.INTERACTABLE;
				}
			}

			return null;
		};
	}
}
