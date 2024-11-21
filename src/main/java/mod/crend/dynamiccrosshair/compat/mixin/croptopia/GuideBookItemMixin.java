//? if croptopia {
package mod.crend.dynamiccrosshair.compat.mixin.croptopia;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.betterx.betterend.item.GuideBookItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = GuideBookItem.class, remap = false)
public class GuideBookItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext crosshairContext) {
		return InteractionType.USE_ITEM;
	}
}
//?}
