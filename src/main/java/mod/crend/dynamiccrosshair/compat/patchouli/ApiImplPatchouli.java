package mod.crend.dynamiccrosshair.compat.patchouli;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.item.ItemStack;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.common.item.PatchouliItems;

public class ApiImplPatchouli implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return PatchouliAPI.MOD_ID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return itemStack.isOf(PatchouliItems.BOOK);
	}
}
