//? if farmers-delight-refabricated {
package mod.crend.dynamiccrosshair.compat.mixin.farmersdelight;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.tag.ModTags;

@Mixin(value = PieBlock.class, remap = false)
public class PieBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItemStack().isIn(ModTags.KNIVES)) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}

		if (context.getPlayer().canConsume(false)) {
			return InteractionType.TAKE_ITEM_FROM_BLOCK;
		}

		return InteractionType.EMPTY;
	}
}
//?}
