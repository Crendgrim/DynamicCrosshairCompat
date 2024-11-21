//? if meadow {
package mod.crend.dynamiccrosshair.compat.mixin.meadow;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.satisfy.meadow.item.WateringCanItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = WateringCanItem.class, remap = false)
public class WateringCanItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		BlockHitResult hitResult = context.raycastWithFluid(RaycastContext.FluidHandling.SOURCE_ONLY);
		BlockPos blockPos;
		if (hitResult.getType() == HitResult.Type.BLOCK) {
			blockPos = hitResult.getBlockPos();
			if (context.getWorld().canPlayerModifyAt(context.getPlayer(), blockPos)) {
				if (context.getWorld().getFluidState(blockPos).isIn(FluidTags.WATER)) {
					return InteractionType.FILL_ITEM_FROM_BLOCK;
				}
			}
		}

		ItemStack itemStack = context.getItemStack();
		if (itemStack.getDamage() < itemStack.getMaxDamage() || context.getPlayer().getAbilities().creativeMode) {
			BlockState blockState = context.getBlockState();
			blockPos = context.getBlockPos();
			if (blockState instanceof Fertilizable fertilizable && fertilizable.isFertilizable(context.getWorld(), blockPos, blockState, true)) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			} else {
				if (blockState.isSideSolidFullSquare(context.getWorld(), blockPos, context.getBlockHitSide())
						&& blockState.isOf(Blocks.WATER) && context.getFluidState().getLevel() == 8) {
					return InteractionType.FILL_ITEM_FROM_BLOCK;
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
