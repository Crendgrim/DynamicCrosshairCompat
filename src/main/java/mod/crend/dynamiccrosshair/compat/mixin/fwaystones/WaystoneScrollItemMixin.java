//? if waystones {
package mod.crend.dynamiccrosshair.compat.mixin.fwaystones;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import wraith.fwaystones.item.WaystoneScrollItem;

@Mixin(value = WaystoneScrollItem.class, remap = false)
public class WaystoneScrollItemMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (itemStack.hasNbt() && itemStack.getNbt().contains("fwaystones")) {
			return InteractionType.USE_ITEM;
		}
		return InteractionType.EMPTY;
	}
}
//?}
