//? if ae2 {
package mod.crend.dynamiccrosshair.compat.mixin.ae2;

import appeng.parts.reporting.AbstractReportingPart;
import appeng.util.InteractionUtil;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = AbstractReportingPart.class, remap = false)
public class AbstractReportingPartMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (InteractionUtil.canWrenchRotate(context.getItemStack())) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
