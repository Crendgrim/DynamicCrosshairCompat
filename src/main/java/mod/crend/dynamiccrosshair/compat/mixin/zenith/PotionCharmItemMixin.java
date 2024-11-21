//? if zenith {
package mod.crend.dynamiccrosshair.compat.mixin.zenith;

import dev.shadowsoffire.apotheosis.potion.PotionCharmItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = PotionCharmItem.class, remap = false)
public class PotionCharmItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return InteractionType.USE_ITEM;
	}
}
//?}
