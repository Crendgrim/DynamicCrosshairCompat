//? if emerald-geodes {
package mod.crend.dynamiccrosshair.compat.mixin.geodes;

import com.github.thedeathlycow.moregeodes.blocks.EchoLocatorBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EchoLocatorBlock.class, remap = false)
public class EchoLocatorBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext crosshairContext) {
		return InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
