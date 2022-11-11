package mod.crend.dynamiccrosshair.compat.advancementframes;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.mehvahdjukaar.advframes.blocks.AdvancementFrameBlock;
import net.minecraft.block.BlockState;

public class ApiImplAdvancementFrames implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "advancementframes";
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof AdvancementFrameBlock;
	}
}
