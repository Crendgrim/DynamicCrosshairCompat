//? if applied-energistics-2 {
package mod.crend.dynamiccrosshair.compat.mixin.ae2;

import appeng.api.parts.IPart;
import appeng.block.networking.CableBusBlock;
import appeng.blockentity.networking.CableBusBlockEntity;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = CableBusBlock.class, remap = false)
public class CableBusBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isMainHand() && context.getBlockEntity() instanceof CableBusBlockEntity busBlockEntity) {
			IPart part = busBlockEntity.selectPartWorld(context.getHitResult().getPos()).part;
			return ((DynamicCrosshairBlock) part).dynamiccrosshair$compute(context);
		}
		return InteractionType.EMPTY;
	}
}
//?}
