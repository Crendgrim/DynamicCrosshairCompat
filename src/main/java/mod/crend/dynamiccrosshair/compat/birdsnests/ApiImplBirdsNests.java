package mod.crend.dynamiccrosshair.compat.birdsnests;

import daniking.birdsnests.BirdsNests;
import daniking.birdsnests.NestItem;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.item.ItemStack;

public class ApiImplBirdsNests implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return BirdsNests.MODID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof NestItem;
	}
}
