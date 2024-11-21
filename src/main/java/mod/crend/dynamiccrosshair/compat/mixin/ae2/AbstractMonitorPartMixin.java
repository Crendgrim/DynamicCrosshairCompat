//? if applied-energistics-2 {
package mod.crend.dynamiccrosshair.compat.mixin.ae2;

import appeng.api.parts.IPartItem;
import appeng.parts.reporting.AbstractDisplayPart;
import appeng.parts.reporting.AbstractMonitorPart;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = AbstractMonitorPart.class, remap = false)
public class AbstractMonitorPartMixin extends AbstractDisplayPart implements DynamicCrosshairBlock {
	public AbstractMonitorPartMixin(IPartItem<?> partItem, boolean requireChannel) {
		super(partItem, requireChannel);
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (this.getMainNode().isActive()) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
