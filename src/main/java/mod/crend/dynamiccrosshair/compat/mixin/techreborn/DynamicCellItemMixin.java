//? if techreborn {
package mod.crend.dynamiccrosshair.compat.mixin.techreborn;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import techreborn.items.DynamicCellItem;

@Mixin(value = DynamicCellItem.class, remap = false)
public abstract class DynamicCellItemMixin implements DynamicCrosshairItem {
	@Shadow public abstract Fluid getFluid(ItemStack itemStack);

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		Fluid containedFluid = this.getFluid(context.getItemStack());
		BlockHitResult fluidHitResult = context.raycastWithFluid(RaycastContext.FluidHandling.SOURCE_ONLY);
		BlockState fluidState = context.getWorld().getBlockState(fluidHitResult.getBlockPos());
		if (containedFluid == Fluids.EMPTY) {
			if (fluidHitResult.getType() == HitResult.Type.BLOCK) {
				if (fluidState.getBlock() instanceof FluidDrainable) {
					return InteractionType.FILL_ITEM_FROM_BLOCK;
				}
			}

		} else if (containedFluid instanceof FlowableFluid) {
			if (fluidState.canBucketPlace(containedFluid)) {
				return InteractionType.PLACE_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
