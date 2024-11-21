package mod.crend.dynamiccrosshair.compat.impl;

//? if extended-drawers
import io.github.mattidragon.extendeddrawers.block.base.StorageDrawerBlock;
import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;

public class ApiImplExtendedDrawers implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "extended_drawers";
	}

	//? if extended-drawers {
	@Override
	public boolean forceInvalidate(CrosshairContext context) {
		return (context.isWithBlock() && context.getBlock() instanceof StorageDrawerBlock<?>);
	}
	//?}
}
