//? if techreborn {
package mod.crend.dynamiccrosshair.compat.mixin.techreborn;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import reborncore.api.blockentity.IMachineGuiHandler;
import techreborn.blocks.storage.energy.EnergyStorageBlock;

@Mixin(value = EnergyStorageBlock.class, remap = false)
public class EnergyStorageBlockMixin implements DynamicCrosshairBlock {
	@Shadow @Final public IMachineGuiHandler gui;

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return this.gui == null ? InteractionType.EMPTY : InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
