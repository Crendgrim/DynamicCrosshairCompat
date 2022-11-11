package mod.crend.dynamiccrosshair.compat.gofish;

import draylar.gofish.item.CrateItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ApiImplGoFish implements DynamicCrosshairApi {

	@Override
	public String getModId() {
		return "go-fish";
	}

	@Override
	public String getNamespace() {
		return "gofish";
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof CrateItem;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (context.includeUsableItem() && !context.isTargeting()) {
			Item item = context.getItem();
			if (context.player.isSneaking() && item instanceof CrateItem) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
