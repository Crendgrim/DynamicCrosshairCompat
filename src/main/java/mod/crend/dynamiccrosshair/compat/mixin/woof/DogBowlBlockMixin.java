//? if woof {
package mod.crend.dynamiccrosshair.compat.mixin.woof;

import com.mineblock11.woof.Woof;
import com.mineblock11.woof.register.block.DogBowlBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = DogBowlBlock.class, remap = false)
public class DogBowlBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItemStack().isIn(Woof.MEATS)) {
			return InteractionType.PLACE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
