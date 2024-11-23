//? if wraith-waystones {
package mod.crend.dynamiccrosshair.compat.mixin.fwaystones;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import wraith.fwaystones.item.WaystoneScrollItem;

//? if >=1.21 {
/*import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
*///?}

@Mixin(value = WaystoneScrollItem.class, remap = false)
public class WaystoneScrollItemMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		boolean hasNbt;
		//? if <1.21 {
		hasNbt = itemStack.hasNbt() && itemStack.getNbt().contains("fwaystones");
		//?} else {
		/*NbtComponent component = itemStack.get(DataComponentTypes.CUSTOM_DATA);
		hasNbt = component != null && component.getNbt() != null && component.getNbt().contains("fwaystones");
		*///?}
		if (hasNbt) {
			return InteractionType.USE_ITEM;
		}
		return InteractionType.EMPTY;
	}
}
//?}
