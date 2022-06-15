package mod.crend.dynamiccrosshair.compat.chipped;

import com.grimbo.chipped.Chipped;
import com.grimbo.chipped.block.ChippedWorkbench;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IBlockInteractHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;

public class ApiImplChipped implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Chipped.MOD_ID;
	}

	@Override
	public IBlockInteractHandler getBlockInteractHandler() {
		return (player, itemStack, blockPos, blockState) -> {
			if (blockState.getBlock() instanceof ChippedWorkbench) {
				return Crosshair.INTERACTABLE;
			}

			return null;
		};
	}
}
