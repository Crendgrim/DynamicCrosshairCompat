//? if vinery {
package mod.crend.dynamiccrosshair.compat.mixin.vinery;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockState;
import net.satisfy.vinery.core.block.AppleLeavesBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = AppleLeavesBlock.class, remap = false)
public class AppleLeavesMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		if (blockState.get(AppleLeavesBlock.VARIANT) && blockState.get(AppleLeavesBlock.HAS_APPLES)) {
			return InteractionType.TAKE_ITEM_FROM_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
