package mod.crend.dynamiccrosshair.compat.moregeodes;

import com.github.thedeathlycow.moregeodes.MoreGeodes;
import com.github.thedeathlycow.moregeodes.blocks.EchoLocatorBlock;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.block.BlockState;

public class ApiImplMoreGeodes implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return MoreGeodes.MODID;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof EchoLocatorBlock;
	}
}
