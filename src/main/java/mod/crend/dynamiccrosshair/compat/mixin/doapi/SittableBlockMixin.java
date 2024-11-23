//? if do-api {
package mod.crend.dynamiccrosshair.compat.mixin.doapi;

//? if =1.20.1
import de.cristelknight.doapi.common.block.BenchBlock;
import de.cristelknight.doapi.common.block.ChairBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = {
		//? if =1.20.1
		BenchBlock.class,
		ChairBlock.class
}, remap = false
)
public class SittableBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (!context.getPlayer().isSneaking() && context.getBlockHitSide() != Direction.DOWN) {
			return InteractionType.MOUNT_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
