//? if villagersplus {
package mod.crend.dynamiccrosshair.compat.mixin.villagersplus;

//? if =1.20.1 {
import com.lion.villagersplus.blocks.OceanographerTableBlock;
import com.lion.villagersplus.init.VPTags;
//?} else {
/*import com.finallion.villagersplus.blocks.OceanographerTableBlock;
import com.finallion.villagersplus.init.ModTags;
*///?}
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = OceanographerTableBlock.class, remap = false)
public class OceanographerTableBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		ItemStack itemStack = context.getItemStack();
		if (itemStack.isIn(/*? if =1.20.1 {*/VPTags.AQUARIUM_PLANTABLE_ITEMS/*?} else {*//*ModTags.AQUARIUM_PLANTABLE_BLOCKS*//*?}*/)
				&& blockState.get(OceanographerTableBlock.CORALS) < 4
		) {
			return InteractionType.PLACE_ITEM_ON_BLOCK;
		}

		if (itemStack.getItem() instanceof EntityBucketItem) {
			if (blockState.get(OceanographerTableBlock.FISH) < 1) {
				return InteractionType.PLACE_ITEM_ON_BLOCK;
			}
		}

		return InteractionType.EMPTY;
	}
}
//?}
