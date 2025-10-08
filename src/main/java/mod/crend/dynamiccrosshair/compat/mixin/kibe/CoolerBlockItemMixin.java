//? if kibe {
package mod.crend.dynamiccrosshair.compat.mixin.kibe;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

//? if <1.21 {
import io.github.lucaargolo.kibe.items.cooler.CoolerBlockItem;
//?} else
/*import io.github.lucaargolo.kibe.item.CoolerBlockItem;*/

@Mixin(value = CoolerBlockItem.class, remap = false)
public class CoolerBlockItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (!context.isWithBlock() || context.getPlayer().isSneaking()) {
			return InteractionType.USE_ITEM;
		}
		return InteractionType.EMPTY;
	}
}
//?}
