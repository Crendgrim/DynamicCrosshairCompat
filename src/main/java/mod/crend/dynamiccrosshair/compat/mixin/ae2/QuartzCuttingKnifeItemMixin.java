//? if ae2 {
package mod.crend.dynamiccrosshair.compat.mixin.ae2;

import appeng.items.tools.quartz.QuartzCuttingKnifeItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = QuartzCuttingKnifeItem.class, remap = false)
public class QuartzCuttingKnifeItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return InteractionType.USE_ITEM;
	}
}
//?}
