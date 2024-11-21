//? if magic-combat-wands {
package mod.crend.dynamiccrosshair.compat.mixin.magiccombatwands;

import dlovin.smalls.magiccombatwands.core.items.WandItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.item.RangedWeaponItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = WandItem.class, remap = false)
public abstract class WandItemMixin extends RangedWeaponItem implements DynamicCrosshairItem {
	public WandItemMixin(Settings settings) {
		super(settings);
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isActiveItem()) {
			float progress = WandItem.getPullProgress(getMaxUseTime(context.getItemStack()) - context.getPlayer().getItemUseTimeLeft());
			if (progress == 1.0F) {
				return InteractionType.RANGED_WEAPON_CHARGED;
			}
			return InteractionType.RANGED_WEAPON_CHARGING;
		}
		return InteractionType.NO_ACTION;
	}
}
//?}
