//? if wetlands {
package mod.crend.dynamiccrosshair.compat.mixin.wetlands;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.birchfolks.wetlands.item.SulfurNugget;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.CandleBlock;
import net.minecraft.block.CandleCakeBlock;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = SulfurNugget.class, remap = false)
public class SulfurNuggetMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock()) {
			BlockPos blockPos = context.getBlockPos();
			BlockState blockState = context.getBlockState();
			if (!CampfireBlock.canBeLit(blockState) && !CandleBlock.canBeLit(blockState) && !CandleCakeBlock.canBeLit(blockState)) {
				BlockPos blockPos2 = blockPos.offset(context.getBlockHitSide());
				if (AbstractFireBlock.canPlaceAt(context.getWorld(), blockPos2, context.getPlayer().getHorizontalFacing())) {
					return InteractionType.PLACE_BLOCK;
				}
			} else {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
