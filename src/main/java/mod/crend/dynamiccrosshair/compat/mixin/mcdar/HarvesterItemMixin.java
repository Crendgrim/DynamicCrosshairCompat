//? if mcdar {
package mod.crend.dynamiccrosshair.compat.mixin.mcdar;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;
import safro.archon.item.HarvesterItem;

@Mixin(value = HarvesterItem.class, remap = false)
public class HarvesterItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getPlayer().totalExperience >= 40 || context.getPlayer().isCreative()) {
			return InteractionType.USE_ITEM;
		}
		return InteractionType.EMPTY;
	}
}
//?}
