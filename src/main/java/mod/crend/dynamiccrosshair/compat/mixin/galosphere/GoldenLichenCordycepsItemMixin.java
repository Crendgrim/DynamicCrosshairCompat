//? if galosphere {
package mod.crend.dynamiccrosshair.compat.mixin.galosphere;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.orcinus.galosphere.api.GoldenBreath;
import net.orcinus.galosphere.items.GoldenLichenCordycepsItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = GoldenLichenCordycepsItem.class, remap = false)
public class GoldenLichenCordycepsItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getPlayer().getAir() < ((GoldenBreath) context.getPlayer()).getMaxGoldenAirSupply()) {
			return InteractionType.USE_ITEM;
		}
		if (context.getPlayer().getAir() < context.getPlayer().getMaxAir()) {
			return InteractionType.USE_ITEM;
		}
		if (context.getItem().getFoodComponent() != null) {
			if (context.getPlayer().canConsume(context.getItem().getFoodComponent().isAlwaysEdible())) {
				return InteractionType.USE_ITEM;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
