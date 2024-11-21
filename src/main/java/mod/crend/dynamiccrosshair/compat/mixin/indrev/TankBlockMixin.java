//? if industrial-revolution {
package mod.crend.dynamiccrosshair.compat.mixin.indrev;

import me.steven.indrev.blocks.misc.TankBlock;
import me.steven.indrev.utils.FluidutilsKt;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.fabric.CrosshairFluidContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = TankBlock.class, remap = false)
public class TankBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (CrosshairFluidContext.canInteractWithFluidStorage(context, FluidutilsKt.fluidStorageOf(context.getWorld(), context.getBlockPos(), context.getBlockHitSide()))) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		 return InteractionType.EMPTY;
	}
}
//?}
