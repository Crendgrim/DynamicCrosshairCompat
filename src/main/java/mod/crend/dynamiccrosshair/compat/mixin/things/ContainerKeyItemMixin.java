//? if things {
package mod.crend.dynamiccrosshair.compat.mixin.things;

import com.glisco.things.items.generic.ContainerKeyItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ContainerKeyItem.class, remap = false)
public class ContainerKeyItemMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext crosshairContext) {
		return null;
	}
}
//?}
