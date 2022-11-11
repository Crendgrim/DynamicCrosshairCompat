package mod.crend.dynamiccrosshair.compat.throwabletorch;

import com.daniking.throwabletorch.ThrowableTorch;
import com.daniking.throwabletorch.ThrowableTorchItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.ItemCategory;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.ItemStack;

public class ApiImplThrowableTorch implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return ThrowableTorch.MOD_ID;
	}

	@Override
	public ItemCategory getItemCategory(ItemStack itemStack) {
		if (itemStack.getItem() instanceof ThrowableTorchItem) {
			return ItemCategory.THROWABLE;
		}
		return ItemCategory.NONE;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (context.includeThrowable() && context.getItem() instanceof ThrowableTorchItem) {
			return Crosshair.THROWABLE;
		}
		return null;
	}
}
