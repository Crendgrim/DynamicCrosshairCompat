//? if meadow {
package mod.crend.dynamiccrosshair.compat.mixin.meadow;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.util.math.Direction;
import net.satisfy.meadow.core.block.BenchBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BenchBlock.class, remap = false)
public class BenchBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (!context.getPlayer().isSneaking() && context.getBlockHitSide() != Direction.DOWN) {
			return InteractionType.MOUNT_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
