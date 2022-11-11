package mod.crend.dynamiccrosshair.compat.bomd;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.ItemCategory;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.barribob.boss.item.ChargedEnderPearlItem;
import net.barribob.boss.item.EarthdiveSpear;
import net.minecraft.item.ItemStack;

public class ApiImplBossesOfMassDestruction implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "bosses_of_mass_destruction";
	}

	@Override
	public ItemCategory getItemCategory(ItemStack itemStack) {
		if (itemStack.getItem() instanceof EarthdiveSpear) {
			return ItemCategory.RANGED_WEAPON;
		}
		if (itemStack.getItem() instanceof ChargedEnderPearlItem) {
			return ItemCategory.THROWABLE;
		}
		return DynamicCrosshairApi.super.getItemCategory(itemStack);
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
	public Crosshair computeFromItem(CrosshairContext context) {
		if (context.includeUsableItem()) {
			Crosshair crosshair = BOMDHandler.checkUsableItem(context);
			if (crosshair != null) return crosshair;
		}
		if (context.includeMeleeWeapon()) {
			return BOMDHandler.checkMeleeWeapon(context);
		}
		return null;
	}
}
