//? if bygone-nether {
package mod.crend.dynamiccrosshair.compat.mixin.bygonenether;

import com.izofar.bygonenether.item.WarpedEnderpearlItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = WarpedEnderpearlItem.class, remap = false)
public class WarpedEnderpearlItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext crosshairContext) {
		return InteractionType.USE_ITEM;
	}
}
//?}
