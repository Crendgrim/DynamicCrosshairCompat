//? if conjuring {
package mod.crend.dynamiccrosshair.compat.mixin.conjuring;

import com.glisco.conjuring.blocks.BlackstonePedestalBlock;
import com.glisco.conjuring.blocks.BlackstonePedestalBlockEntity;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BlackstonePedestalBlock.class, remap = false)
public class BlackstonePedestalBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof BlackstonePedestalBlockEntity pedestal && !pedestal.isActive()) {
			if (pedestal.getItem().isEmpty()) {
				if (!context.getItemStack().isEmpty()) {
					return InteractionType.PLACE_ITEM_ON_BLOCK;
				}
			} else {
				return InteractionType.TAKE_ITEM_FROM_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
