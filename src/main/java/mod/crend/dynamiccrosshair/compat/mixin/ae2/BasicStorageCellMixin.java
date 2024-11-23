//? if ae2 {
package mod.crend.dynamiccrosshair.compat.mixin.ae2;

import appeng.items.storage.BasicStorageCell;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BasicStorageCell.class, remap = false)
public class BasicStorageCellMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return context.getPlayer().isSneaking() ? InteractionType.USE_ITEM : InteractionType.EMPTY;
	}
}
//?}
