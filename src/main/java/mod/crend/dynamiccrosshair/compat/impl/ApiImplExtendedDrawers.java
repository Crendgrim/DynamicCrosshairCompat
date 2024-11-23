package mod.crend.dynamiccrosshair.compat.impl;

//? if extended-drawers {
//? if =1.20.1 {
import io.github.mattidragon.extendeddrawers.block.base.StorageDrawerBlock;
//?} else
/*import io.github.mattidragon.extendeddrawers.block.DrawerBlock;*/
//?}
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
		return (context.isWithBlock() && context.getBlock() instanceof /*? =1.20.1 {*/StorageDrawerBlock<?>/*?} else {*//*DrawerBlock*//*?}*/);
	}
	//?}
}
