//? if bosses-of-mass-destruction {
package mod.crend.dynamiccrosshair.compat.mixin.bosses_of_mass_destruction;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.barribob.boss.block.ChiseledStoneAltarBlock;
import net.barribob.boss.block.ModBlocks;
import net.barribob.boss.item.SoulStarItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = SoulStarItem.class, remap = false)
public class SoulStarItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock() && context.getBlockState().isOf(ModBlocks.INSTANCE.getChiseledStoneAltar())) {
			if (!context.getBlockState().get(ChiseledStoneAltarBlock.Companion.getLit())) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
			return InteractionType.EMPTY;
		}
		return InteractionType.USE_ITEM;
	}
}
//?}
