//? if vinery {
package mod.crend.dynamiccrosshair.compat.mixin.vinery;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import org.spongepowered.asm.mixin.Mixin;
import satisfyu.vinery.block.StackableLogBlock;

@Mixin(value = StackableLogBlock.class, remap = false)
public class StackableLogBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		ItemStack itemStack = context.getItemStack();
		SlabType stackSize = blockState.get(StackableLogBlock.TYPE);
		if (itemStack.isOf(Items.FLINT_AND_STEEL) && stackSize == SlabType.DOUBLE) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		} else if (itemStack.getItem() instanceof ShovelItem && stackSize == SlabType.DOUBLE && blockState.get(StackableLogBlock.FIRED)) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
