//? if waystones {
package mod.crend.dynamiccrosshair.compat.mixin.fwaystones;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import wraith.fwaystones.item.LocalVoidItem;
import wraith.fwaystones.item.VoidTotem;

@Mixin(value = LocalVoidItem.class, remap = false)
public class LocalVoidItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (itemStack.hasNbt() && itemStack.getNbt().contains("fwaystones")) {
			if (context.getPlayer().isSneaking()) {
				return InteractionType.USE_ITEM;
			} else if (!(itemStack.getItem() instanceof VoidTotem)) {
				return InteractionType.USE_ITEM;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
