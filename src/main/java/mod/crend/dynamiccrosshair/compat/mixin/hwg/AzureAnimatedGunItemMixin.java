//? if happiness-is-a-warm-gun {
package mod.crend.dynamiccrosshair.compat.mixin.hwg;

import mod.azure.hwg.item.weapons.AzureAnimatedGunItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = AzureAnimatedGunItem.class, remap = false)
public class AzureAnimatedGunItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext crosshairContext) {
		return InteractionType.RANGED_WEAPON;
	}
}
//?}
