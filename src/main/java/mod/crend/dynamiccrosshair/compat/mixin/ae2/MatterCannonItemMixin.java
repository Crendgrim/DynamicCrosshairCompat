//? if applied-energistics-2 {
package mod.crend.dynamiccrosshair.compat.mixin.ae2;

import appeng.items.tools.powered.MatterCannonItem;
import appeng.items.tools.powered.powersink.AEBasePoweredItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.DoubleSupplier;

@Mixin(value = MatterCannonItem.class, remap = false)
public abstract class MatterCannonItemMixin extends AEBasePoweredItem implements DynamicCrosshairItem {
	public MatterCannonItemMixin(DoubleSupplier powerCapacity, Settings props) {
		super(powerCapacity, props);
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (this.getAECurrentPower(context.getItemStack()) > 1600.0) {
			return InteractionType.RANGED_WEAPON_CHARGED;
		}
		return InteractionType.EMPTY;
	}
}
//?}
