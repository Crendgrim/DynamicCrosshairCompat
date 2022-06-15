package mod.crend.dynamiccrosshair.compat.akashictomeoftools;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.item.ItemStack;
import vazkii.akashictomeoftools.AkashicTome;

public class ApiImplAkashicTomeOfTools implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "akashictomeoftools";
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return itemStack.isOf(AkashicTome.TOME_ITEM);
	}
}
