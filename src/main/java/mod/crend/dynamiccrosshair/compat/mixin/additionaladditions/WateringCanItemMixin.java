//? if addadd {
package mod.crend.dynamiccrosshair.compat.mixin.additionaladditions;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.GrassBlock;
import net.minecraft.item.ItemStack;
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
		if (context.isWithBlock()) {
			BlockHitResult hitResult = context.raycastWithFluid(RaycastContext.FluidHandling.SOURCE_ONLY);
			if (hitResult.getType() == HitResult.Type.BLOCK) {
				BlockPos wateredBlockPos = hitResult.getBlockPos();
				BlockState wateredBlockState = context.getWorld().getBlockState(wateredBlockPos);
				Block wateredBlock = wateredBlockState.getBlock();
				ItemStack handItemStack = context.getItemStack();
				if (handItemStack.getDamage() > 0 || context.getPlayer().isCreative()) {
					if (wateredBlock instanceof Fertilizable && !(wateredBlock instanceof GrassBlock)) {
						return InteractionType.USE_ITEM_ON_BLOCK;
					}
					if (wateredBlock instanceof FarmlandBlock) {
						return InteractionType.USE_ITEM_ON_BLOCK;
					}
				}

				if (handItemStack.getDamage() < 100) {
					if (wateredBlock == Blocks.WATER) {
						return InteractionType.FILL_ITEM_FROM_BLOCK;
					}
				}
			}
		}
		return null;
	}
}
//?}
