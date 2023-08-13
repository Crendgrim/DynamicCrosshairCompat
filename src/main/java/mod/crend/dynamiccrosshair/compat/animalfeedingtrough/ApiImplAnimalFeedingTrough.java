package mod.crend.dynamiccrosshair.compat.animalfeedingtrough;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.block.BlockState;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;
import slexom.animal_feeding_trough.platform.common.block.FeedingTroughBlock;

public class ApiImplAnimalFeedingTrough implements DynamicCrosshairApi {

	@Override
	public String getNamespace() {
		return AnimalFeedingTroughMod.MOD_ID;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof FeedingTroughBlock;
	}
}
