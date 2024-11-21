package mod.crend.dynamiccrosshair.compat.impl;

import mod.crend.dynamiccrosshair.compat.helper.BCLibHandler;
import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;

public class ApiImplBetterNether implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "betternether";
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (context.includeUsableItem()) {
			return BCLibHandler.checkUsableItem(context);
		}
		return null;
	}
}
