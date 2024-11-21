//? if advancement-frames {
package mod.crend.dynamiccrosshair.compat.mixin.advancementframes;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.mehvahdjukaar.advframes.blocks.AdvancementFrameBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = AdvancementFrameBlock.class, remap = false)
public class AdvancementFrameBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext crosshairContext) {
		return InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
