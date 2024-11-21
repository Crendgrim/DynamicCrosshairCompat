//? if applied-energistics-2 {
package mod.crend.dynamiccrosshair.compat.mixin.ae2;

import appeng.parts.automation.EnergyLevelEmitterPart;
import appeng.parts.automation.FormationPlanePart;
import appeng.parts.automation.IOBusPart;
import appeng.parts.automation.StorageLevelEmitterPart;
import appeng.parts.crafting.PatternProviderPart;
import appeng.parts.misc.InterfacePart;
import appeng.parts.reporting.AbstractTerminalPart;
import appeng.parts.reporting.PatternAccessTerminalPart;
import appeng.parts.storagebus.StorageBusPart;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = {
		EnergyLevelEmitterPart.class,
		FormationPlanePart.class,
		IOBusPart.class,
		StorageLevelEmitterPart.class,
		PatternProviderPart.class,
		InterfacePart.class,
		StorageBusPart.class,
		AbstractTerminalPart.class,
		PatternAccessTerminalPart.class
}, remap = false)
public class InteractablePartsMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
