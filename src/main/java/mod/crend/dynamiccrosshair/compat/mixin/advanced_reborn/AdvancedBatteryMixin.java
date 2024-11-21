//? if advanced-reborn {
package mod.crend.dynamiccrosshair.compat.mixin.advanced_reborn;

import ml.pkom.advancedreborn.items.AdvancedBattery;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = AdvancedBattery.class, remap = false)
public class AdvancedBatteryMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getPlayer().isSneaking()) return InteractionType.USE_ITEM;
		return InteractionType.EMPTY;
	}
}
//?}
