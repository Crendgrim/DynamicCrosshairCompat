package mod.crend.dynamiccrosshair.compat.trinkets;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.ItemStack;

public class ApiImplTrinkets implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "trinkets";
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return TrinketsHandler.isUsableItem(itemStack);
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		return TrinketsHandler.computeFromItem(context);
	}

}
