package mod.crend.dynamiccrosshair.compat.omnihopper;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.block.BlockState;
import nl.enjarai.omnihopper.OmniHopper;
import nl.enjarai.omnihopper.blocks.HopperBlock;

public class ApiImplOmniHopper implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return OmniHopper.MODID;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof HopperBlock;
	}
}
