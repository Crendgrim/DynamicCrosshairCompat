//? if merequester {
package mod.crend.dynamiccrosshair.compat.mixin.merequester;

import com.almostreliable.merequester.requester.RequesterBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = RequesterBlock.class, remap = false)
public class RequesterBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return context.getPlayer().isSneaking() ? InteractionType.EMPTY : InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
