//? if another-furniture {
package mod.crend.dynamiccrosshair.compat.mixin.another_furniture;

import com.starfish_studios.another_furniture.block.ShelfBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ShelfBlock.class, remap = false)
public class ShelfBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockHitSide() == Direction.UP) {
			if (context.getItemStack().isEmpty()) {
				return InteractionType.TAKE_ITEM_FROM_BLOCK;
			}
			return InteractionType.PLACE_ITEM_ON_BLOCK;
		}
		return null;
	}
}
//?}
