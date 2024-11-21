//? if adventurez {
package mod.crend.dynamiccrosshair.compat.mixin.adventurez;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.adventurez.item.PrimeEyeItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = PrimeEyeItem.class, remap = false)
public class PrimeEyeItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack handItemStack = context.getItemStack();
		if (handItemStack.getDamage() <= handItemStack.getMaxDamage() - 1) {
			return InteractionType.THROW_ITEM;
		}
		return InteractionType.EMPTY;
	}
}
//?}
