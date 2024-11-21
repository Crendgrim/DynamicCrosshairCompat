//? if addadd {
package mod.crend.dynamiccrosshair.compat.mixin.additionaladditions;

import dqu.additionaladditions.item.GlowStickItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = GlowStickItem.class, remap = false)
public class GlowStickItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return InteractionType.USE_ITEM;
	}
}
//?}
