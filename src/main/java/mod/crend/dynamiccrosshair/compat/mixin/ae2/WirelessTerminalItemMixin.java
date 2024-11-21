//? if applied-energistics-2 {
package mod.crend.dynamiccrosshair.compat.mixin.ae2;

import appeng.items.tools.powered.WirelessTerminalItem;
import appeng.items.tools.powered.powersink.AEBasePoweredItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.DoubleSupplier;

@Mixin(value = WirelessTerminalItem.class, remap = false)
public abstract class WirelessTerminalItemMixin extends AEBasePoweredItem implements DynamicCrosshairItem {
	public WirelessTerminalItemMixin(DoubleSupplier powerCapacity, Settings props) {
		super(powerCapacity, props);
	}

	@Shadow public abstract boolean hasPower(PlayerEntity player, double amt, ItemStack is);

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (this.hasPower(context.getPlayer(), 0.5, context.getItemStack())) {
			return InteractionType.USE_ITEM;
		}
		return InteractionType.EMPTY;
	}
}
//?}
