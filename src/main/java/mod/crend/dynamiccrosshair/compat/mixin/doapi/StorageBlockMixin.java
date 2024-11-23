//? if do-api {
package mod.crend.dynamiccrosshair.compat.mixin.doapi;

import de.cristelknight.doapi.Util;
import de.cristelknight.doapi.common.block.StorageBlock;
import de.cristelknight.doapi.common.block.entity.StorageBlockEntity;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(value = StorageBlock.class, remap = false)
public abstract class StorageBlockMixin implements DynamicCrosshairBlock {

	@Shadow public abstract Direction[] unAllowedDirections();

	@Shadow public abstract int getSection(Float aFloat, Float aFloat1);

	@Shadow public abstract boolean canInsertStack(ItemStack itemStack);

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		Optional<Pair<Float, Float>> optional = Util.getRelativeHitCoordinatesForBlockFace(context.getBlockHitResult(), context.getBlockState().get(StorageBlock.FACING), this.unAllowedDirections());
		if (optional.isPresent() && context.getBlockEntity() instanceof StorageBlockEntity storageBlockEntity) {
			Pair<Float, Float> ff = optional.get();
			int i = this.getSection(ff.getLeft(), ff.getRight());
			if (i != Integer.MIN_VALUE) {
				ItemStack itemStack = context.getItemStack();
				if (!(storageBlockEntity.getInventory().get(i)).isEmpty()) {
					return InteractionType.TAKE_ITEM_FROM_BLOCK;
				} else if (!itemStack.isEmpty() && this.canInsertStack(itemStack)) {
					return InteractionType.PLACE_ITEM_ON_BLOCK;
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
