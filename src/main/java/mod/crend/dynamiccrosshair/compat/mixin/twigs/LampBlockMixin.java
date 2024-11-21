//? if twigs {
package mod.crend.dynamiccrosshair.compat.mixin.twigs;

import com.ninni.twigs.block.LampBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = LampBlock.class, remap = false)
public class LampBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return context.getPlayer().isSneaking() ? InteractionType.EMPTY : InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
