//? if vinery {
package mod.crend.dynamiccrosshair.compat.mixin.vinery;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import satisfyu.vinery.block.AppleLeaves;

@Mixin(value = AppleLeaves.class, remap = false)
public class AppleLeavesMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		if (blockState.get(AppleLeaves.VARIANT) && blockState.get(AppleLeaves.HAS_APPLES)) {
			return InteractionType.TAKE_ITEM_FROM_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
