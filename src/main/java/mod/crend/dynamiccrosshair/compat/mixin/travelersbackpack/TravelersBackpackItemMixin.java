//? if travelers_backpack {
package mod.crend.dynamiccrosshair.compat.mixin.travelersbackpack;

import com.tiviacz.travelersbackpack.items.TravelersBackpackItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = TravelersBackpackItem.class, remap = false)
public class TravelersBackpackItemMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (!context.getPlayer().isSneaking()) {
			return InteractionType.USE_ITEM;
		}
		return InteractionType.EMPTY;
	}
}
//?}
