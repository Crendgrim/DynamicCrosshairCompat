package mod.crend.dynamiccrosshair.compat.magiccombatwands;

import dlovin.smalls.magiccombatwands.core.items.WandItem;
import mod.crend.dynamiccrosshair.DynamicCrosshair;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.ItemCategory;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.config.UsableCrosshairPolicy;
import net.minecraft.item.ItemStack;

public class ApiImplMagicCombatWands implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "magiccombatwands";
	}

	@Override
	public ItemCategory getItemCategory(ItemStack itemStack) {
		if (itemStack.getItem() instanceof WandItem) {
			return ItemCategory.RANGED_WEAPON;
		}
		return DynamicCrosshairApi.super.getItemCategory(itemStack);
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();

		if (itemStack.getItem() instanceof WandItem wand) {
			if (DynamicCrosshair.config.dynamicCrosshairHoldingRangedWeapon() == UsableCrosshairPolicy.Always) {
				return Crosshair.RANGED_WEAPON;
			} else if (context.isActiveItem()) {
				float progress = WandItem.getPullProgress(wand.getMaxUseTime(itemStack) - context.player.getItemUseTimeLeft());
				if (progress == 1.0F) {
					return Crosshair.RANGED_WEAPON;
				}
			}
			return Crosshair.REGULAR.withFlag(Crosshair.Flag.FixedModifierUse);
		}
		return null;
	}
}
