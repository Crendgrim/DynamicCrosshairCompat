package mod.crend.dynamiccrosshair.compat.animalfeedingtrough;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import slexom.vf.animal_feeding_trough.AnimalFeedingTroughMod;

public class ApiImplAnimalFeedingTrough implements DynamicCrosshairApi {

	@Override
	public String getNamespace() {
		return AnimalFeedingTroughMod.MOD_ID;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return blockState.isOf(AnimalFeedingTroughMod.FEEDING_TROUGH_BLOCK);
	}
}
