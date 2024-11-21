//? if bclib {
package mod.crend.dynamiccrosshair.compat.mixin.bclib;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.betterx.bclib.furniture.block.AbstractChair;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = AbstractChair.class, remap = false)
public class AbstractChairMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return InteractionType.MOUNT_BLOCK;
	}
}
//?}
