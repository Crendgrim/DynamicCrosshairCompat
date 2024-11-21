//? if galosphere {
package mod.crend.dynamiccrosshair.compat.mixin.galosphere;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.orcinus.galosphere.blocks.WarpedAnchorBlock;
import net.orcinus.galosphere.init.GBlocks;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = WarpedAnchorBlock.class, remap = false)
public class WarpedAnchorBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItemStack().isOf(GBlocks.ALLURITE_BLOCK.asItem()) && context.getBlockState().get(WarpedAnchorBlock.WARPED_CHARGE) < 4) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
