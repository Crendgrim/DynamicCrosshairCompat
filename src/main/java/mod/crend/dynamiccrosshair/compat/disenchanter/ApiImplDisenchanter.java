package mod.crend.dynamiccrosshair.compat.disenchanter;

import com.glisco.disenchanter.Disenchanter;
import com.glisco.disenchanter.DisenchanterBlock;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;

public class ApiImplDisenchanter implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Disenchanter.MOD_ID;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		if (context.getBlock() instanceof DisenchanterBlock) {
			return Crosshair.INTERACTABLE;
		}

		return null;
	}
}
