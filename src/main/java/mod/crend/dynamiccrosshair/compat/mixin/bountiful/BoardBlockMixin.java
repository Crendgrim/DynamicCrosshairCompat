//? if bountiful {
package mod.crend.dynamiccrosshair.compat.mixin.bountiful;

import io.ejekta.bountiful.content.BountyItem;
import io.ejekta.bountiful.content.board.BoardBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BoardBlock.class, remap = false)
public class BoardBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItem() instanceof BountyItem) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		} else {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
	}
}
//?}
