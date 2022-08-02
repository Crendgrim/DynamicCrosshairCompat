package mod.crend.dynamiccrosshair.compat.enderchests;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.item.ItemStack;
import shetiphian.enderchests.EnderChests;
import shetiphian.enderchests.common.item.ItemEnderBag;
import shetiphian.enderchests.common.item.ItemEnderPouch;

public class ApiImplEnderChests implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return EnderChests.MOD_ID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return (itemStack.getItem() instanceof ItemEnderPouch || itemStack.getItem() instanceof ItemEnderBag);
	}
}
