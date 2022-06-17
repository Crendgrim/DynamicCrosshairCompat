package mod.crend.dynamiccrosshair.compat.trinkets;

import dev.emi.trinkets.TrinketSlot;
import dev.emi.trinkets.TrinketsMain;
import dev.emi.trinkets.api.*;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.ItemStack;

public class ApiImplTrinkets implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return TrinketsMain.MOD_ID;
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof TrinketItem;
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		if (context.getItem() instanceof TrinketItem) {
			var optional = TrinketsApi.getTrinketComponent(context.player);
			if (optional.isPresent()) {
				TrinketComponent comp = optional.get();
				for (var group : comp.getInventory().values()) {
					for (TrinketInventory inv : group.values()) {
						for (int i = 0; i < inv.size(); i++) {
							if (inv.getStack(i).isEmpty()) {
								SlotReference ref = new SlotReference(inv, i);
								if (TrinketSlot.canInsert(context.getItemStack(), ref, context.player)) {
									return Crosshair.USE_ITEM;
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
}
