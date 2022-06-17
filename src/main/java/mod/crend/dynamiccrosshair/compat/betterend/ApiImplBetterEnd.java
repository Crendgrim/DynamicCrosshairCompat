package mod.crend.dynamiccrosshair.compat.betterend;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.bclib.BCLibUsableItemHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import ru.betterend.BetterEnd;

public class ApiImplBetterEnd implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return BetterEnd.MOD_ID;
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		return BCLibUsableItemHandler.checkUsableItem(context);
	}
}
