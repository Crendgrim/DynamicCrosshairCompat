package mod.crend.dynamiccrosshair.compat.appliedenergistics;

import appeng.core.AppEng;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.ItemStack;

public class ApiImplAppliedEnergistics2 implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return AppEng.MOD_ID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return AE2Handler.isAlwaysUsableItem(itemStack);
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return AE2Handler.isUsableItem(itemStack);
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		return AE2Handler.checkUsableItem(context);
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		return AE2Handler.checkBlockInteractable(context);
	}
}
