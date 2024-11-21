//? if addadd {
package mod.crend.dynamiccrosshair.compat.mixin.additionaladditions;

import dqu.additionaladditions.block.CopperPatinaBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = CopperPatinaBlock.class, remap = false)
public abstract class CopperPatinaBlockMixin implements DynamicCrosshairBlock {

	@Shadow
	private static boolean isFullyConnected(BlockState state) {
		return false;
	}

	@Shadow
	private static boolean isNotConnected(BlockState state) {
		return false;
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		if (isFullyConnected(blockState) || isNotConnected(blockState)) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
