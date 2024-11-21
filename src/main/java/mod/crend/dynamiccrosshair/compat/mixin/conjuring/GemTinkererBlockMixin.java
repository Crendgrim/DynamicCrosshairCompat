//? if conjuring {
package mod.crend.dynamiccrosshair.compat.mixin.conjuring;

import com.glisco.conjuring.blocks.gem_tinkerer.GemTinkererBlock;
import com.glisco.conjuring.blocks.gem_tinkerer.GemTinkererBlockEntity;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = GemTinkererBlock.class, remap = false)
public class GemTinkererBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isMainHand() && context.getBlockEntity() instanceof GemTinkererBlockEntity tinkerer) {
			if (tinkerer.isRunning()) {
				if (tinkerer.isCraftingComplete() && context.getItemStack().isEmpty()) {
					return InteractionType.TAKE_ITEM_FROM_BLOCK;
				}
			} else {
				if (context.getItemStack().isEmpty()) {
					return InteractionType.TAKE_ITEM_FROM_BLOCK;
				}
				return InteractionType.PLACE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
