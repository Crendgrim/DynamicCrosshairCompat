//? if extended-drawers {
package mod.crend.dynamiccrosshair.compat.mixin.extended_drawers;

import io.github.mattidragon.extendeddrawers.block.AccessPointBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = AccessPointBlock.class, remap = false)
public class AccessPointBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItemStack().isEmpty()) {
			return InteractionType.INTERACT_WITH_BLOCK;
		} else {
			return InteractionType.PLACE_ITEM_ON_BLOCK;
		}
	}
}
//?}
