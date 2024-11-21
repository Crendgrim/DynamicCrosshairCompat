//? if paladins-furniture {
package mod.crend.dynamiccrosshair.compat.mixin.pfm;

import com.unlikepaladin.pfm.items.LightSwitchItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = LightSwitchItem.class, remap = false)
public class LightSwitchItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return context.getPlayer().isSneaking() ? InteractionType.USE_ITEM : InteractionType.EMPTY;
	}
}
//?}
