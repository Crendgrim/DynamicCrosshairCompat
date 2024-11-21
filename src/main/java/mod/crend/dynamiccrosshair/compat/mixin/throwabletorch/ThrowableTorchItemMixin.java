//? if throwabletorch-fabric {
package mod.crend.dynamiccrosshair.compat.mixin.throwabletorch;

import com.daniking.throwabletorch.ThrowableTorchItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ThrowableTorchItem.class, remap = false)
public class ThrowableTorchItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext crosshairContext) {
		return InteractionType.THROW_ITEM;
	}
}
//?}
