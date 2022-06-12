package mod.crend.dynamiccrosshair.compat.adapaxels;

import com.brand.adapaxels.AdaPaxels;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IUsableItemHandler;

public class ApiImplAdaPaxels implements DynamicCrosshairApi {

	@Override
	public String getNamespace() {
		return AdaPaxels.MOD_ID;
	}

	AdaPaxelsUsableItemHandler usableItemHandler = new AdaPaxelsUsableItemHandler();

	@Override
	public IUsableItemHandler getUsableItemHandler() {
		return usableItemHandler;
	}

}
