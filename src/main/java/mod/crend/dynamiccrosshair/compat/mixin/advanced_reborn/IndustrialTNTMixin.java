//? if advanced-reborn {
package mod.crend.dynamiccrosshair.compat.mixin.advanced_reborn;

import ml.pkom.advancedreborn.blocks.IndustrialTNT;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = IndustrialTNT.class, remap = false)
public class IndustrialTNTMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
