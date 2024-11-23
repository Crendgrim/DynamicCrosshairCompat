//? if ae2 {
package mod.crend.dynamiccrosshair.compat.mixin.ae2;

import appeng.block.misc.CrankBlock;
import appeng.block.networking.ControllerBlock;
import appeng.block.storage.SkyChestBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = {
		CrankBlock.class,
		ControllerBlock.class,
		SkyChestBlock.class
}, remap = false)
public class AlwaysInteractableBlocksMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext crosshairContext) {
		return InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
