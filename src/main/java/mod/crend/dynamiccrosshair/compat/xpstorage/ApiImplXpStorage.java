package mod.crend.dynamiccrosshair.compat.xpstorage;

import com.notker.xp_storage.XpStorage;
import com.notker.xp_storage.items.HandBookItem;
import com.notker.xp_storage.items.Xp_removerItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ApiImplXpStorage implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return XpStorage.MOD_ID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof HandBookItem || item instanceof Xp_removerItem;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		return XpStorageHandler.computeFromBlock(context);
	}
}
