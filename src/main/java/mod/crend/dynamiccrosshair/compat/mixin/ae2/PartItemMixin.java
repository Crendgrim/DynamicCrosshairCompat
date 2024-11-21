//? if applied-energistics-2 {
package mod.crend.dynamiccrosshair.compat.mixin.ae2;

import appeng.items.parts.PartItem;
import appeng.parts.PartPlacement;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = PartItem.class, remap = false)
public class PartItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock() && PartPlacement.canPlacePartOnBlock(context.getPlayer(), context.getWorld(), context.getItemStack(), context.getBlockPos(), context.getBlockHitSide())) {
			return InteractionType.PLACE_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
