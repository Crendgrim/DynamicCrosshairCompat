//? if another-furniture {
package mod.crend.dynamiccrosshair.compat.mixin.another_furniture;

import com.starfish_studios.another_furniture.block.FlowerBoxBlock;
import com.starfish_studios.another_furniture.registry.AFItemTags;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = FlowerBoxBlock.class, remap = false)
public class FlowerBoxBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItemStack().isIn(AFItemTags.FLOWER_BOX_PLACEABLES) && !context.getItemStack().isIn(AFItemTags.FLOWER_BOX_BANNED)) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
