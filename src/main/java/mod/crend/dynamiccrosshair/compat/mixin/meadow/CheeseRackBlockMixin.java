//? if meadow {
package mod.crend.dynamiccrosshair.compat.mixin.meadow;

import de.cristelknight.doapi.Util;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Direction;
import net.satisfy.meadow.block.entity.CheeseRackBlockEntity;
import net.satisfy.meadow.block.storage.CheeseRackBlock;
import net.satisfy.meadow.registry.TagRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(value = CheeseRackBlock.class, remap = false)
public abstract class CheeseRackBlockMixin implements DynamicCrosshairBlock {
	@Shadow public abstract Direction[] unAllowedDirections();

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (!context.getPlayer().isSneaking() && context.getBlockEntity() instanceof CheeseRackBlockEntity be) {
			Optional<Pair<Float, Float>> optional = Util.getRelativeHitCoordinatesForBlockFace(context.getBlockHitResult(), context.getBlockState().get(CheeseRackBlock.FACING), this.unAllowedDirections());
			if (optional.isPresent()) {
				int slot = optional.get().getRight() > 0.5 ? 1 : 0;

				ItemStack itemStack = context.getItemStack();
				if (itemStack.isEmpty() && be.hasStack(slot)) {
					return InteractionType.TAKE_ITEM_FROM_BLOCK;
				} else if (itemStack.isIn(TagRegistry.CHEESE_BLOCKS) && !be.hasStack(slot)) {
					return InteractionType.PLACE_ITEM_ON_BLOCK;
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
