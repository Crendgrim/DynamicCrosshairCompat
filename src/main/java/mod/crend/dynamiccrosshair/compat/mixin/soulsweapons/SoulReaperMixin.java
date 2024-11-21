//? if mariums-soulslike-weaponry {
package mod.crend.dynamiccrosshair.compat.mixin.soulsweapons;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.item.ItemStack;
import net.soulsweaponry.items.SoulReaper;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = SoulReaper.class, remap = false)
public class SoulReaperMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (itemStack.hasNbt() && itemStack.getNbt().contains("kills")) {
			int power = itemStack.getNbt().getInt("kills");
			if (power >= 3) {
				return InteractionType.USE_ITEM;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
