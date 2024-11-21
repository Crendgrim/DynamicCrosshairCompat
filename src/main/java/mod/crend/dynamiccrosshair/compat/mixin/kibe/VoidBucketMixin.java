//? if kibe {
package mod.crend.dynamiccrosshair.compat.mixin.kibe;

import io.github.lucaargolo.kibe.items.miscellaneous.VoidBucket;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = VoidBucket.class, remap = false)
public class VoidBucketMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock()) {
			if (context.getBlock() instanceof FluidDrainable) {
				return InteractionType.FILL_ITEM_FROM_BLOCK;
			}
		}
		if (!context.isWithEntity()) {
			BlockHitResult fluidHitResult = context.raycastWithFluid(RaycastContext.FluidHandling.SOURCE_ONLY);
			if (fluidHitResult.getType() != HitResult.Type.MISS) {
				BlockState fluidBlockState = context.getWorld().getBlockState(fluidHitResult.getBlockPos());
				if (fluidBlockState.getBlock() instanceof FluidDrainable) {
					return InteractionType.FILL_ITEM_FROM_BLOCK;
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
