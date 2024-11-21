//? if natures-compass {
package mod.crend.dynamiccrosshair.compat.mixin.naturescompass;

import com.chaosthedude.naturescompass.items.NaturesCompassItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = NaturesCompassItem.class, remap = false)
public class NaturesCompassItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return InteractionType.USE_ITEM;
	}
}
//?}
