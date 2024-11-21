//? if applied-energistics-2 {
package mod.crend.dynamiccrosshair.compat.mixin.ae2;

import appeng.block.storage.SkyStoneTankBlock;
import appeng.blockentity.storage.SkyStoneTankBlockEntity;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.fabric.CrosshairFluidContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = SkyStoneTankBlock.class, remap = false)
public class SkyStoneTankBlockMixin extends AEBaseEntityBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof SkyStoneTankBlockEntity blockEntity) {
			if (CrosshairFluidContext.canInteractWithFluidStorage(context, blockEntity.getStorage())) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}
		return super.dynamiccrosshair$compute(context);
	}
}
//?}
