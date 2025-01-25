//? if meadow {
package mod.crend.dynamiccrosshair.compat.mixin.meadow;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.satisfy.meadow.core.block.FrameBlock;
import net.satisfy.meadow.core.registry.ObjectRegistry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = FrameBlock.class, remap = false)
public class FrameBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItemStack().isOf(ObjectRegistry.COOKING_CAULDRON.get().asItem())) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
