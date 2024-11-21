//? if conjuring {
package mod.crend.dynamiccrosshair.compat.mixin.conjuring;

import com.glisco.conjuring.items.soul_alloy_tools.SoulAlloyTool;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = SoulAlloyTool.class, remap = false)
public class SoulAlloyToolMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (SoulAlloyTool.isSecondaryEnabled(context.getItemStack())) {
			return InteractionType.USE_ITEM;
		}
		return InteractionType.EMPTY;
	}
}
//?}
