//? if vinery {
package mod.crend.dynamiccrosshair.compat.mixin.vinery;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ShearsItem;
import net.satisfy.vinery.block.CherryLeavesBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = CherryLeavesBlock.class, remap = false)
public class CherryLeavesMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		if (blockState.get(CherryLeavesBlock.VARIANT) && blockState.get(CherryLeavesBlock.HAS_CHERRIES)) {
			if (context.getItem() instanceof ShearsItem) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
			return InteractionType.TAKE_ITEM_FROM_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
