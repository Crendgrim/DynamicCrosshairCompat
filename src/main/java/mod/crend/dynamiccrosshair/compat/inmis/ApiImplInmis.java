package mod.crend.dynamiccrosshair.compat.inmis;

import draylar.inmis.Inmis;
import draylar.inmis.item.BackpackItem;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.item.ItemStack;

public class ApiImplInmis implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "inmis";
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		if (itemStack.isOf(Inmis.ENDER_POUCH)) {
			return true;
		}

		return itemStack.getItem() instanceof BackpackItem && !Inmis.CONFIG.requireArmorTrinketToOpen;
	}
}
