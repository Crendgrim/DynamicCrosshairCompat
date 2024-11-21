//? if conjuring {
package mod.crend.dynamiccrosshair.compat.mixin.conjuring;

import com.glisco.conjuring.blocks.conjurer.ConjurerBlock;
import com.glisco.conjuring.items.ConjuringScepter;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ConjurerBlock.class, remap = false)
public class ConjurerBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItem() instanceof ConjuringScepter) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
