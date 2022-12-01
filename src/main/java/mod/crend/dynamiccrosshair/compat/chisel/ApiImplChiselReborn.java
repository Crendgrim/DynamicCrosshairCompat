package mod.crend.dynamiccrosshair.compat.chisel;

import com.matthewperiut.chisel.item.ChiselItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.ItemStack;

public class ApiImplChiselReborn implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "chisel";
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof ChiselItem;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (context.getItem() instanceof ChiselItem && context.isMainHand()) {
			return Crosshair.USABLE;
		}

		return null;
	}
}
