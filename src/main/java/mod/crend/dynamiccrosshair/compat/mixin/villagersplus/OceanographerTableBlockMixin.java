//? if villagersplus-fabric {
package mod.crend.dynamiccrosshair.compat.mixin.villagersplus;

import com.lion.villagersplus.blocks.OceanographerTableBlock;
import com.lion.villagersplus.init.VPTags;
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
		if (itemStack.isIn(VPTags.AQUARIUM_PLANTABLE_ITEMS) && blockState.get(OceanographerTableBlock.CORALS) < 4) {
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
