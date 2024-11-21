//? if wetlands {
package mod.crend.dynamiccrosshair.compat.mixin.wetlands;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.birchfolks.wetlands.block.GearBlock;
import net.birchfolks.wetlands.block.WetlandsBlocks;
import net.birchfolks.wetlands.item.WetlandsItems;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = GearBlock.class, remap = false)
public class GearBlockMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlock() != WetlandsBlocks.GEAR_BLOCK_ACTIVE && context.getItemStack().isOf(WetlandsItems.BOTTLED_WISP)) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
