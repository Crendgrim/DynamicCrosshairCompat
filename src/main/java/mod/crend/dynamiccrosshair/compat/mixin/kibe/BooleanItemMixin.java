//? if kibe {
package mod.crend.dynamiccrosshair.compat.mixin.kibe;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

//? if <1.21 {
import io.github.lucaargolo.kibe.items.miscellaneous.BooleanItem;
//?} else
/*import io.github.lucaargolo.kibe.item.BooleanItem;*/

@Mixin(value = BooleanItem.class, remap = false)
public class BooleanItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return context.getPlayer().isSneaking() ? InteractionType.USE_ITEM : InteractionType.EMPTY;
	}
}
//?}
