//? if archon {
package mod.crend.dynamiccrosshair.compat.mixin.archon;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import safro.archon.item.ManaItem;
import safro.archon.item.earth.TerraneanAxeItem;
import safro.archon.util.ArchonUtil;

@Mixin(value = {ManaItem.class, TerraneanAxeItem.class}, remap = false)
public abstract class ManaItemMixin implements DynamicCrosshairItem {
	@Shadow public abstract int getManaCost();

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (ArchonUtil.canRemoveMana(context.getPlayer(), getManaCost())) {
			return InteractionType.USE_ITEM;
		}
		return InteractionType.EMPTY;
	}
}
//?}
