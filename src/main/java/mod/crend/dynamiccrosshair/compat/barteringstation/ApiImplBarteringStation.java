package mod.crend.dynamiccrosshair.compat.barteringstation;

import fuzs.barteringstation.BarteringStation;
import fuzs.barteringstation.world.level.block.BarteringStationBlock;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.block.BlockState;

public class ApiImplBarteringStation implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return BarteringStation.MOD_ID;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof BarteringStationBlock;
	}
}
