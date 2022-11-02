package mod.crend.dynamiccrosshair.compat.techreborn;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.item.ItemStack;
import org.abos.fabricmc.trpatchouli.GuideBook;

public class ApiImplTechRebornPatchouli implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "tr-patchouli";
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof GuideBook;
	}
}
