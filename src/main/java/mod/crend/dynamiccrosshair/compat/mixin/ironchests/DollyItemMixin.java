//? if ironchestsrestocked {
package mod.crend.dynamiccrosshair.compat.mixin.ironchests;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import tech.thatgravyboat.ironchests.common.items.DollyItem;

@Mixin(value = DollyItem.class, remap = false)
public class DollyItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock()) {
			ItemStack itemStack = context.getItemStack();
			if (DollyItem.hasChest(itemStack) && context.getPlayer().isSneaking()) {
				return InteractionType.PLACE_BLOCK;
			}
			if (!context.getBlockState().isIn(DollyItem.NONPICKABLE_CHEST_TAG) && !DollyItem.hasChest(itemStack)) {
				return InteractionType.PICK_UP_BLOCK;
			}
		}
		return null;
	}
}
//?}
