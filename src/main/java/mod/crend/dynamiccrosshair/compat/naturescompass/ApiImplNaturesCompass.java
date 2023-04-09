package mod.crend.dynamiccrosshair.compat.naturescompass;

import com.chaosthedude.naturescompass.NaturesCompass;
import com.chaosthedude.naturescompass.items.NaturesCompassItem;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.item.ItemStack;

public class ApiImplNaturesCompass implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return NaturesCompass.MODID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof NaturesCompassItem;
	}
}
