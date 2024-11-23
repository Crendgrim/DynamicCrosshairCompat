//? if more-geodes {
package mod.crend.dynamiccrosshair.compat.mixin.geodes;

import com.github.thedeathlycow.moregeodes.item.CrystalLocatorItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = CrystalLocatorItem.class, remap = false)
public class CrystalLocatorItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext crosshairContext) {
		return InteractionType.USE_ITEM;
	}
}
//?}
