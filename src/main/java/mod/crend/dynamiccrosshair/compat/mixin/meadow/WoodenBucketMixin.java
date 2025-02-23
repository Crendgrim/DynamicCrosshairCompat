//? if meadow {
package mod.crend.dynamiccrosshair.compat.mixin.meadow;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidDrainable;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.satisfy.meadow.core.item.WoodenBucket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = WoodenBucket.class, remap = false)
public class WoodenBucketMixin implements DynamicCrosshairItem {
	//? if =1.20.1 {
	@Shadow @Final private Fluid content;
	//?} else
	/*@Shadow @Final private Fluid fluid;*/

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		//? if =1.20.1
		Fluid fluid = this.content;
		BlockHitResult blockHitResult = context.raycastWithFluid(fluid == Fluids.EMPTY ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);
		if (blockHitResult.getType() == HitResult.Type.BLOCK) {
			BlockPos blockPos = blockHitResult.getBlockPos();
			Direction direction = blockHitResult.getSide();
			BlockPos blockPos2 = blockPos.offset(direction);
			if (context.getWorld().canPlayerModifyAt(context.getPlayer(), blockPos) && context.getPlayer().canPlaceOn(blockPos2, direction, context.getItemStack())) {
				BlockState blockState = context.getWorld().getBlockState(blockPos);
				if (fluid == Fluids.EMPTY) {
					if (blockState.getBlock() instanceof FluidDrainable) {
						if (!blockState.getBlock().equals(Blocks.LAVA)) {
							return InteractionType.FILL_ITEM_FROM_BLOCK;
						}
					}
				} else {
					if (fluid instanceof FlowableFluid) {
						return InteractionType.FILL_ITEM_FROM_BLOCK;
					}
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
