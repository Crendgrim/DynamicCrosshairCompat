//? if voidz {
package mod.crend.dynamiccrosshair.compat.mixin.voidz;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.voidz.block.PortalBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = PortalBlock.class, remap = false)
public class PortalBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
