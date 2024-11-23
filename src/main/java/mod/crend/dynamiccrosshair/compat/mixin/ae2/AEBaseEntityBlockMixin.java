//? if ae2 {
package mod.crend.dynamiccrosshair.compat.mixin.ae2;

import appeng.api.implementations.items.IMemoryCard;
import appeng.block.AEBaseEntityBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = AEBaseEntityBlock.class, remap = false)
public class AEBaseEntityBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItem() instanceof IMemoryCard) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
