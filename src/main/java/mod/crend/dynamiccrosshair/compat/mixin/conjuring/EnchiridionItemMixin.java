//? if conjuring {
package mod.crend.dynamiccrosshair.compat.mixin.conjuring;

import com.glisco.conjuring.items.EnchiridionItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EnchiridionItem.class, remap = false)
public class EnchiridionItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext crosshairContext) {
		return InteractionType.USE_ITEM;
	}
}
//?}
