package mod.crend.dynamiccrosshair.compat.trinkets;

import dev.emi.trinkets.TrinketSlot;
import dev.emi.trinkets.api.*;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.ItemStack;

public class TrinketsHandler {
	public static boolean isUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof TrinketItem;
	}

	public static Crosshair computeFromItem(CrosshairContext context) {
		if (context.includeUsableItem() && context.getItem() instanceof TrinketItem && canEquipTrinket(context)) {
			return Crosshair.USABLE;
		}
		return null;
	}

	public static boolean canEquipTrinket(CrosshairContext context) {
		var optional = TrinketsApi.getTrinketComponent(context.player);
		if (optional.isPresent()) {
			TrinketComponent comp = optional.get();
			for (var group : comp.getInventory().values()) {
				for (TrinketInventory inv : group.values()) {
					for (int i = 0; i < inv.size(); i++) {
						if (inv.getStack(i).isEmpty()) {
							SlotReference ref = new SlotReference(inv, i);
							if (TrinketSlot.canInsert(context.getItemStack(), ref, context.player)) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
}
