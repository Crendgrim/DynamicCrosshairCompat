package mod.crend.dynamiccrosshair.compat.mixin.early_buckets;

import dev.satyrn.early_buckets.item.CustomPowderSnowBucketItem;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CustomPowderSnowBucketItem.class)
public interface ICustomPowderSnowBucketItemMixin {
	@Invoker
	boolean invokeCanPlace(ItemPlacementContext context, BlockState state);
}
