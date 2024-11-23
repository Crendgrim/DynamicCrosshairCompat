package mod.crend.dynamiccrosshair.compat.impl;

//? if toms-storage
import com.tom.storagemod.item.WirelessTerminalItem;
import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;

public class ApiImplStorageMod implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "toms_storage";
	}

	//? if toms-storage {
	@Override
	public boolean forceInvalidate(CrosshairContext context) {
		return !context.isTargeting() && context.getItem() instanceof WirelessTerminalItem;
	}
	//?}
}
