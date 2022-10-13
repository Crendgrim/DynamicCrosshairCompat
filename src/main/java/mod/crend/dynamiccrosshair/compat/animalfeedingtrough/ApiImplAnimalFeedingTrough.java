package mod.crend.dynamiccrosshair.compat.animalfeedingtrough;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import slexom.vf.animal_feeding_trough.AnimalFeedingTroughMod;

public class ApiImplAnimalFeedingTrough implements DynamicCrosshairApi {

	@Override
	public String getNamespace() {
		return AnimalFeedingTroughMod.MOD_ID;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		if (context.getBlockState().isOf(AnimalFeedingTroughMod.FEEDING_TROUGH_BLOCK)) {
			return Crosshair.INTERACTABLE;
		}

		return null;
	}
}
