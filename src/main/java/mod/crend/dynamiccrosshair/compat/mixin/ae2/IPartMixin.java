//? if applied-energistics-2 {
package mod.crend.dynamiccrosshair.compat.mixin.ae2;

import appeng.api.parts.IPart;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = IPart.class, remap = false)
public interface IPartMixin extends DynamicCrosshairBlock {
	@Override
	default InteractionType dynamiccrosshair$compute(CrosshairContext crosshairContext) {
		return InteractionType.EMPTY;
	}
}
//?}
