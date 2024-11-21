//? if adorn {
package mod.crend.dynamiccrosshair.compat.mixin.adorn;

import juuxel.adorn.block.SeatBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = SeatBlock.class, remap = false)
public abstract class SeatBlockMixin implements DynamicCrosshairBlock {
	@Shadow protected abstract boolean isSittingEnabled();

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext crosshairContext) {
		if (isSittingEnabled()) return InteractionType.MOUNT_BLOCK;
		return InteractionType.EMPTY;
	}
}
//?}
