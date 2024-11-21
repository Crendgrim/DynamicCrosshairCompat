//? if techreborn {
package mod.crend.dynamiccrosshair.compat.mixin.techreborn;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;
import techreborn.items.BatteryItem;
import techreborn.items.FrequencyTransmitterItem;
import techreborn.items.ManualItem;
import techreborn.items.ScrapBoxItem;

@Mixin(value = {
		BatteryItem.class,
		FrequencyTransmitterItem.class,
		ManualItem.class,
		ScrapBoxItem.class
}, remap = false)
public class AlwaysUsableItemsMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return InteractionType.USE_ITEM;
	}
}
//?}
