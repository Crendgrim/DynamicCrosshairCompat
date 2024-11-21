//? if addadd {
package mod.crend.dynamiccrosshair.compat.mixin.additionaladditions;

import dqu.additionaladditions.item.MysteriousBundleItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = MysteriousBundleItem.class, remap = false)
public class MysteriousBundleItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return InteractionType.USE_ITEM;
	}
}
//?}
