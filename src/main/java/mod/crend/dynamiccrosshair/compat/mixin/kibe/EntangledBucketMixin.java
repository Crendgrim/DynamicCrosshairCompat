//? if kibe {
package mod.crend.dynamiccrosshair.compat.mixin.kibe;

import io.github.lucaargolo.kibe.blocks.entangledtank.EntangledTank;
import io.github.lucaargolo.kibe.items.entangledbucket.EntangledBucket;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.FluidFillable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EntangledBucket.class, remap = false)
public class EntangledBucketMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock() && context.getPlayer().isSneaking() && context.getBlock() instanceof EntangledTank) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}

		NbtCompound tag = EntangledBucket.Companion.getTag(context.getItemStack());
		StringBuilder colorCode = new StringBuilder();
		for (int i = 1; i <= 8; i++) {
			DyeColor dc = DyeColor.byName(tag.getString("rune" + i), DyeColor.WHITE);
			colorCode.append(Integer.toHexString(dc.getId()));
		}
		tag.putString("colorCode", colorCode.toString());
		SingleVariantStorage<FluidVariant> fluidInv = EntangledBucket.Companion.getFluidInv(context.getWorld(), tag);
		Fluid fluid;
		if (fluidInv.isResourceBlank()) {
			fluid = Fluids.EMPTY;
		} else {
			fluid = fluidInv.getResource().getFluid();
		}
		boolean hasSpace = (fluidInv.getAmount() + FluidConstants.BUCKET) <= fluidInv.getCapacity();
		boolean hasBucket = fluidInv.getAmount() >= FluidConstants.BUCKET;

		BlockHitResult hitResult = context.raycastWithFluid(hasSpace ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);
		if (hitResult != null) {
			boolean isBucket = (context.getPlayer().isSneaking() || fluid.getBucketItem() == Items.BUCKET);
			if (isBucket && hasSpace) {
				return InteractionType.FILL_ITEM_FROM_BLOCK;
			}
			Direction dir = hitResult.getSide();
			BlockPos pos = hitResult.getBlockPos();
			BlockPos offsetPos = pos.offset(dir);
			if (context.getPlayer().canPlaceOn(offsetPos, dir, context.getItemStack())) {
				BlockState blockState = context.getWorld().getBlockState(pos);
				if (blockState.getBlock() instanceof FluidDrainable) {
					if (hasSpace) {
						return InteractionType.FILL_ITEM_FROM_BLOCK;
					}
				} else if (hasBucket) {
					BlockPos interactablePos = (blockState.getBlock() instanceof FluidFillable && fluid == Fluids.WATER) ? pos : offsetPos;
					BlockState interactableBlockState = context.getWorld().getBlockState(interactablePos);
					if (!(interactableBlockState.getBlock() instanceof FluidDrainable)
							|| (interactableBlockState.contains(Properties.LEVEL_15) && interactableBlockState.get(Properties.LEVEL_15) != 0)) {
						return InteractionType.FILL_BLOCK_FROM_ITEM;
					}
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
