//? if twigs {
package mod.crend.dynamiccrosshair.compat.mixin.twigs;

import com.ninni.twigs.item.TwigItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = TwigItem.class, remap = false)
public class TwigItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock() && context.getItemStack(Hand.OFF_HAND).isOf(context.getItemStack(Hand.MAIN_HAND).getItem())) {
			return InteractionType.PLACE_BLOCK;
		}
		return InteractionType.THROW_ITEM;
	}
}
//?}
