//? if inmis {
package mod.crend.dynamiccrosshair.compat.mixin.inmis;

import draylar.inmis.Inmis;
import draylar.inmis.item.BackpackItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BackpackItem.class, remap = false)
public class BackpackItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext crosshairContext) {
		return Inmis.CONFIG.requireArmorTrinketToOpen ? InteractionType.EMPTY : InteractionType.USE_ITEM;
	}
}
//?}
