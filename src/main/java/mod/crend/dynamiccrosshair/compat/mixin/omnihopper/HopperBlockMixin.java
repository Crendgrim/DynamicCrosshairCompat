//? if omni-hopper {
package mod.crend.dynamiccrosshair.compat.mixin.omnihopper;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import nl.enjarai.omnihopper.blocks.hopper.HopperBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = HopperBlock.class, remap = false)
public class HopperBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext crosshairContext) {
		return InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
