//? if techreborn {
package mod.crend.dynamiccrosshair.compat.mixin.techreborn;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import techreborn.blocks.misc.BlockRubberLog;
import techreborn.items.tool.TreeTapItem;
import techreborn.items.tool.basic.ElectricTreetapItem;

@Mixin(value = BlockRubberLog.class, remap = false)
public class BlockRubberLogMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();
		if (item instanceof TreeTapItem
				|| (item instanceof ElectricTreetapItem electricTap && electricTap.getStoredEnergy(itemStack) > 20L)
		) {
			if (blockState.get(BlockRubberLog.HAS_SAP) && blockState.get(BlockRubberLog.SAP_SIDE) == context.getBlockHitSide()) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
