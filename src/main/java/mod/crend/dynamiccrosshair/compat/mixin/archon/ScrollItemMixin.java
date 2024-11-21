//? if archon {
package mod.crend.dynamiccrosshair.compat.mixin.archon;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;
import safro.archon.item.ScrollItem;
import safro.archon.util.ArchonUtil;

@Mixin(value = ScrollItem.class, remap = false)
public class ScrollItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (!ArchonUtil.hasScroll(context.getPlayer())) {
			return InteractionType.USE_ITEM;
		}
		return InteractionType.EMPTY;
	}
}
//?}
