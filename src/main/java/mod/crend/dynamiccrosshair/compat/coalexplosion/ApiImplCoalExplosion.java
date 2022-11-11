package mod.crend.dynamiccrosshair.compat.coalexplosion;

import com.natamus.coalexplosion.CommonClass;
import com.natamus.coalexplosion.platform.FabricPlatformHelper;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;

public class ApiImplCoalExplosion implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "coalexplosion";
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (context.isWithBlock()
				&& context.includeUsableItem()
				&& CommonClass.isIgniterStack(context.getItemStack())
				&& context.getBlockState().isIn(FabricPlatformHelper.ORES_COAL)
		) {
			return Crosshair.USABLE;
		}
		return null;
	}
}
