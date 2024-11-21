//? if alloy_forgery {
package mod.crend.dynamiccrosshair.compat.mixin.alloy_forgery;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;
import wraith.alloyforgery.block.ForgeControllerBlock;
import wraith.alloyforgery.block.ForgeControllerBlockEntity;
import wraith.alloyforgery.forges.ForgeFuelRegistry;

@Mixin(value = ForgeControllerBlock.class, remap = false)
public class ForgeControllerBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof ForgeControllerBlockEntity controller) {
			ForgeFuelRegistry.ForgeFuelDefinition fuelDefinition = ForgeFuelRegistry.getFuelForItem(context.getItem());

			if (fuelDefinition.hasReturnType() && controller.canAddFuel(fuelDefinition.fuel())) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			} else {
				if (controller.verifyMultiblock()) {
					return InteractionType.INTERACT_WITH_BLOCK;
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
