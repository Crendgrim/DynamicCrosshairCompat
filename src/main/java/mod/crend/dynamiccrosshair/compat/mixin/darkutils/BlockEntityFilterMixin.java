//? if dark-utilities {
package mod.crend.dynamiccrosshair.compat.mixin.darkutils;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.darkhax.darkutilities.features.filters.BlockEntityFilter;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BlockEntityFilter.class, remap = false)
public class BlockEntityFilterMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getPlayer().isSneaking()) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
