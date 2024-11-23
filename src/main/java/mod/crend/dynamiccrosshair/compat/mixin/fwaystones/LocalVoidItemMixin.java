//? if wraith-waystones {
package mod.crend.dynamiccrosshair.compat.mixin.fwaystones;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import wraith.fwaystones.item.LocalVoidItem;
import wraith.fwaystones.item.VoidTotem;

//? if >=1.21 {
/*import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
*///?}

@Mixin(value = LocalVoidItem.class, remap = false)
public class LocalVoidItemMixin implements DynamicCrosshairItem {

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
