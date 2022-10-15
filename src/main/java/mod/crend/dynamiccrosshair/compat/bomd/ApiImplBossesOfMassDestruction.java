package mod.crend.dynamiccrosshair.compat.bomd;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.ItemStack;

public class ApiImplBossesOfMassDestruction implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "bosses_of_mass_destruction";
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return BOMDHandler.isAlwaysUsableItem(itemStack);
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return BOMDHandler.isUsableItem(itemStack);
	}

	@Override
	public Crosshair checkMeleeWeapon(CrosshairContext context) {
		return BOMDHandler.checkMeleeWeapon(context);
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		return BOMDHandler.checkUsableItem(context);
	}
}
