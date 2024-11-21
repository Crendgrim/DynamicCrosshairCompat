//? if villagersplus-fabric {
package mod.crend.dynamiccrosshair.compat.mixin.villagersplus;

import com.lion.villagersplus.blocks.HorticulturistTableBlock;
import com.lion.villagersplus.init.VPTags;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = HorticulturistTableBlock.class, remap = false)
public class HorticulturistTableBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		ItemStack itemStack = context.getItemStack();
		if (itemStack.isIn(VPTags.TALL_PLANTABLE_ITEMS) && blockState.get(HorticulturistTableBlock.FLOWERS) == 0) {
			return InteractionType.PLACE_ITEM_ON_BLOCK;
		}
		if (itemStack.isIn(VPTags.SMALL_PLANTABLE_ITEMS) && blockState.get(HorticulturistTableBlock.FLOWERS) < 4) {
			return InteractionType.PLACE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
