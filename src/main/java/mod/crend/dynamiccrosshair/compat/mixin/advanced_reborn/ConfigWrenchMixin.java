//? if advanced-reborn {
package mod.crend.dynamiccrosshair.compat.mixin.advanced_reborn;

import ml.pkom.advancedreborn.items.ConfigWrench;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;
import reborncore.common.blockentity.MachineBaseBlockEntity;

@Mixin(value = ConfigWrench.class, remap = false)
public class ConfigWrenchMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof MachineBaseBlockEntity) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
