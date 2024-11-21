//? if techreborn {
package mod.crend.dynamiccrosshair.compat.mixin.techreborn;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;
import techreborn.items.tool.DebugToolItem;

@Mixin(value = DebugToolItem.class, remap = false)
public class DebugToolItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return context.isWithBlock() ? InteractionType.USE_ITEM_ON_BLOCK : InteractionType.EMPTY;
	}
}
//?}
