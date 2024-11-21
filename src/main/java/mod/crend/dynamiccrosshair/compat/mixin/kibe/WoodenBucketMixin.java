//? if kibe {
package mod.crend.dynamiccrosshair.compat.mixin.kibe;

import io.github.lucaargolo.kibe.items.miscellaneous.WoodenBucket;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = WoodenBucket.class, remap = false)
public class WoodenBucketMixin implements DynamicCrosshairItem {
	@Shadow @Final private Fluid fluid;

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (fluid == Fluids.EMPTY) {
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
		} else if (context.isWithBlock()) {
			if (context.getBlock() instanceof Waterloggable && fluid == Fluids.WATER) {
				return InteractionType.FILL_BLOCK_FROM_ITEM;
			}
			return InteractionType.PLACE_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
