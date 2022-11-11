package mod.crend.dynamiccrosshair.compat.simplebackpack;

import com.kwpugh.simple_backpack.Backpack;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.ItemStack;

public class ApiImplSimpleBackpack implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Backpack.MOD_ID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return itemStack.isOf(Backpack.BACKPACK)
				|| itemStack.isOf(Backpack.ENDER_PACK)
				|| itemStack.isOf(Backpack.VOID_PACK);
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return itemStack.isOf(Backpack.PORTABLE_CRAFTER)
				|| itemStack.isOf(Backpack.SIMPLE_BUNDLE)
				|| itemStack.isOf(Backpack.VOID_BUNDLE);
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (!context.includeUsableItem()) return null;

		ItemStack itemStack = context.getItemStack();

		if (itemStack.isOf(Backpack.PORTABLE_CRAFTER) && !context.player.isSneaking()) {
			return Crosshair.USABLE;
		}
		if (itemStack.isOf(Backpack.SIMPLE_BUNDLE) || itemStack.isOf(Backpack.VOID_BUNDLE)) {
			if (context.player.isSneaking() && itemStack.hasNbt() && itemStack.getNbt().contains("Items")) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
