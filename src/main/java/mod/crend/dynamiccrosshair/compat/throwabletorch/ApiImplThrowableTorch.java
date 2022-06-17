package mod.crend.dynamiccrosshair.compat.throwabletorch;

import com.daniking.throwabletorch.ThrowableTorch;
import com.daniking.throwabletorch.ThrowableTorchItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;

public class ApiImplThrowableTorch implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return ThrowableTorch.MOD_ID;
	}

	@Override
	public Crosshair checkThrowable(CrosshairContext context) {
		if (context.getItem() instanceof ThrowableTorchItem) {
			return Crosshair.THROWABLE;
		}
		return null;
	}
}
