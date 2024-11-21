//? if advanced-reborn {
package mod.crend.dynamiccrosshair.compat.mixin.advanced_reborn;

import ml.pkom.advancedreborn.items.FreqTrans;
import ml.pkom.advancedreborn.tile.TeleporterTile;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = FreqTrans.class, remap = false)
public class FreqTransMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof TeleporterTile) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
