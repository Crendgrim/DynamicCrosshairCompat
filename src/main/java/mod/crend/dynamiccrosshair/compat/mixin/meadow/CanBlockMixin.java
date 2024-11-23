//? if meadow {
package mod.crend.dynamiccrosshair.compat.mixin.meadow;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.MilkBucketItem;
//? if =1.20.1 {
import net.satisfy.meadow.block.CanBlock;
import net.satisfy.meadow.registry.ObjectRegistry;
//?} else {
/*import net.satisfyu.meadow.block.CanBlock;
import net.satisfyu.meadow.registry.ObjectRegistry;
*///?}
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = CanBlock.class, remap = false)
public class CanBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		Item item = context.getItem();
		if (item instanceof BucketItem || item instanceof MilkBucketItem) {
			BlockState blockState = context.getBlockState();
			if (blockState.get(CanBlock.FLUID) == 0 && (
					item.equals(Items.MILK_BUCKET)
							|| item.equals(Items.WATER_BUCKET)
							|| item.equals(ObjectRegistry.WOODEN_MILK_BUCKET.get())
							|| item.equals(ObjectRegistry.WOODEN_WATER_BUCKET.get())
			)) {
				return InteractionType.FILL_BLOCK_FROM_ITEM;
			}

			if ((blockState.get(CanBlock.FLUID) == 1 || blockState.get(CanBlock.FLUID) == 2) && (item.equals(Items.BUCKET) || item.equals(ObjectRegistry.WOODEN_BUCKET.get()))) {
				return InteractionType.FILL_ITEM_FROM_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
