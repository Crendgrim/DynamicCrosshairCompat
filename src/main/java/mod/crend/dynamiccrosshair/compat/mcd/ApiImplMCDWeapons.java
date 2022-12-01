package mod.crend.dynamiccrosshair.compat.mcd;

import chronosacaria.mcdw.Mcdw;
import chronosacaria.mcdw.bases.McdwStaff;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.component.ModifierUse;
import net.minecraft.item.Item;

public class ApiImplMCDWeapons implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Mcdw.MOD_ID;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		Item item = context.getItem();
		if (item instanceof McdwStaff && context.isWithBlock()) {
			return new Crosshair(ModifierUse.NONE);
		}
		return null;
	}
}
