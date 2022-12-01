package mod.crend.dynamiccrosshair.compat.windchimes;

import hibiii.windchimes.WindchimeBlock;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.block.BlockState;

public class ApiImplWindchimes implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "windchimes";
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof WindchimeBlock;
	}
}
