//? if vinery {
package mod.crend.dynamiccrosshair.compat.mixin.vinery;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import satisfyu.vinery.block.CalendarBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CalendarBlock.class)
public class CalendarBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return context.getPlayer().isSneaky() ? InteractionType.EMPTY : InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
