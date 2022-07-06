package mod.crend.dynamiccrosshair.compat.hookshot;

import dev.cammiescorner.hookshot.Hookshot;
import dev.cammiescorner.hookshot.common.item.HookshotItem;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.item.ItemStack;

public class ApiImplHookshot implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Hookshot.MOD_ID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof HookshotItem;
	}
}
