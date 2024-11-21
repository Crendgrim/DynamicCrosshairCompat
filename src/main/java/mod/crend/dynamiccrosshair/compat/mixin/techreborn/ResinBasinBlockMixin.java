//? if techreborn {
package mod.crend.dynamiccrosshair.compat.mixin.techreborn;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import techreborn.blocks.machine.tier1.ResinBasinBlock;

@Mixin(value = ResinBasinBlock.class, remap = false)
public class ResinBasinBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		if (blockState.get(ResinBasinBlock.FULL) && !blockState.get(ResinBasinBlock.POURING)) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
