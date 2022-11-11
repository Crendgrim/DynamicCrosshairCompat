package mod.crend.dynamiccrosshair.compat.voidz;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.block.BlockState;
import net.voidz.block.PortalBlock;

public class ApiImplVoidZ implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "voidz";
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof PortalBlock;
	}
}
