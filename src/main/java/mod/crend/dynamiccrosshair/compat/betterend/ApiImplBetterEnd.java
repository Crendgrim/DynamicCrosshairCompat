package mod.crend.dynamiccrosshair.compat.betterend;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IUsableItemHandler;
import mod.crend.dynamiccrosshair.compat.bclib.BCLibUsableItemHandler;
import ru.betterend.BetterEnd;

public class ApiImplBetterEnd implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return BetterEnd.MOD_ID;
	}

	@Override
	public IUsableItemHandler getUsableItemHandler() {
		return new BCLibUsableItemHandler();
	}
}
