//? if mc-dungeons-artifacts {
package mod.crend.dynamiccrosshair.compat.mixin.mcdar;

import chronosacaria.mcdar.artifacts.SoulHealerItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = SoulHealerItem.class, remap = false)
public class SoulHealerItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getPlayer().totalExperience >= 20 || context.getPlayer().isCreative()) {
			return InteractionType.USE_ITEM;
		}
		return InteractionType.EMPTY;
	}
}
//?}
