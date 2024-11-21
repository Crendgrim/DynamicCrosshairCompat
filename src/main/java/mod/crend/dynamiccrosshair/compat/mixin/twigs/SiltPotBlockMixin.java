//? if twigs {
package mod.crend.dynamiccrosshair.compat.mixin.twigs;

import com.ninni.twigs.block.enums.SiltPotBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = SiltPotBlock.class, remap = false)
public class SiltPotBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack stack = context.getItemStack();
		if (!context.getBlockState().get(SiltPotBlock.FILLED)) {
			if (stack.isOf(Blocks.ROOTED_DIRT.asItem())) {
				return InteractionType.PLACE_ITEM_ON_BLOCK;
			}
			return InteractionType.INTERACT_WITH_BLOCK;
		} else {
			if (context.getBlockHitSide() == Direction.UP
					&& stack.getItem() instanceof BlockItem blockItem
					&& blockItem.getBlock() instanceof FlowerBlock
			) {
				return InteractionType.PLACE_BLOCK;
			}

			if (stack.getItem() instanceof ShovelItem) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
