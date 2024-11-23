//? if vinery {
package mod.crend.dynamiccrosshair.compat.mixin.vinery;

import mod.crend.dynamiccrosshair.compat.mixin.doapi.StorageBlockMixin;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.satisfy.vinery.block.storage.WineBoxBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = WineBoxBlock.class, remap = false)
public abstract class WineBoxMixin extends StorageBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getPlayer().isSneaking() && context.getItemStack().isEmpty()) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		else if (!context.getBlockState().get(WineBoxBlock.OPEN)) {
			return InteractionType.EMPTY;
		}
		return super.dynamiccrosshair$compute(context);
	}
}
//?}
