//? if another-furniture {
package mod.crend.dynamiccrosshair.compat.mixin.another_furniture;

import com.starfish_studios.another_furniture.block.BenchBlock;
import com.starfish_studios.another_furniture.registry.AFItemTags;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BenchBlock.class, remap = false)
public abstract class BenchBlockMixin extends SeatBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItemStack().isIn(AFItemTags.FURNITURE_HAMMER)) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return super.dynamiccrosshair$compute(context);
	}
}
//?}
