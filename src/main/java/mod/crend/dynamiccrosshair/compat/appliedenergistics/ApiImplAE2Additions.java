package mod.crend.dynamiccrosshair.compat.appliedenergistics;

import com.the9grounds.aeadditions.item.storage.StorageCell;
import com.the9grounds.aeadditions.item.storage.SuperStorageCell;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ApiImplAE2Additions implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "ae2additions";
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof StorageCell || item instanceof SuperStorageCell;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		Item item = context.getItem();
		if (item instanceof StorageCell || item instanceof SuperStorageCell) {
			if (context.player.isSneaking()) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
