//? if villagersplus-fabric {
package mod.crend.dynamiccrosshair.compat.mixin.villagersplus;

//? if =1.20.1 {
import com.lion.villagersplus.blocks.HorticulturistTableBlock;
import com.lion.villagersplus.init.VPTags;
//?} else {
/*import com.finallion.villagersplus.blocks.HorticulturistTableBlock;
import com.finallion.villagersplus.init.ModTags;
*///?}
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
		if (itemStack.isIn(/*? if =1.20.1 {*/VPTags.TALL_PLANTABLE_ITEMS/*?} else {*//*ModTags.TALL_PLANTABLE_BLOCKS*//*?}*/)
				&& blockState.get(HorticulturistTableBlock.FLOWERS) == 0
		) {
			return InteractionType.PLACE_ITEM_ON_BLOCK;
		}
		if (itemStack.isIn(/*? if =1.20.1 {*/VPTags.SMALL_PLANTABLE_ITEMS/*?} else {*//*ModTags.SMALL_PLANTABLE_BLOCKS*//*?}*/)
				&& blockState.get(HorticulturistTableBlock.FLOWERS) < 4
		) {
			return InteractionType.PLACE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
