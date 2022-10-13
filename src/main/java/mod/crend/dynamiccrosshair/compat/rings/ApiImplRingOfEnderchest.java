package mod.crend.dynamiccrosshair.compat.rings;

import com.kwpugh.ring_of_enderchest.RingOfEnderchest;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.item.ItemStack;

public class ApiImplRingOfEnderchest implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return RingOfEnderchest.MOD_ID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return itemStack.isOf(RingOfEnderchest.RING_OF_ENDERCHEST);
	}
}
