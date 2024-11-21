//? if another-furniture {
package mod.crend.dynamiccrosshair.compat.mixin.another_furniture;

import com.starfish_studios.another_furniture.block.LampBlock;
import com.starfish_studios.another_furniture.registry.AFItemTags;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = LampBlock.class, remap = false)
public class LampBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (!context.getItemStack().isIn(AFItemTags.LAMPS)
				|| context.getBlockState().get(LampBlock.FACING) != Direction.UP
				|| context.getBlockHitSide() != Direction.UP
		) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
