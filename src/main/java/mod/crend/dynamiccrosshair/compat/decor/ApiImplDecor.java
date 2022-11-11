package mod.crend.dynamiccrosshair.compat.decor;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.gmsgarcia.decor4fabric.blocks.*;
import net.gmsgarcia.decor4fabric.mainDecor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class ApiImplDecor implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return mainDecor.MOD_ID;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof workBench;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return     block instanceof logBench
				|| block instanceof logBench2
				|| block instanceof logBench3
				|| block instanceof logChair
				|| block instanceof logSmallStool;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		Block block = context.getBlock();
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
	}
}
