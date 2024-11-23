//? if mc-dungeons-weapons {
package mod.crend.dynamiccrosshair.compat.mixin.mcdw;

import chronosacaria.mcdw.bases.McdwStaff;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = McdwStaff.class, remap = false)
public class McdwStaffMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return context.isWithBlock() ? InteractionType.NO_ACTION : InteractionType.EMPTY;
	}
}
//?}
