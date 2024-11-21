//? if twigs {
package mod.crend.dynamiccrosshair.compat.mixin.twigs;

import com.ninni.twigs.item.PebbleItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = PebbleItem.class, remap = false)
public class PebbleItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return InteractionType.THROW_ITEM;
	}
}
//?}
