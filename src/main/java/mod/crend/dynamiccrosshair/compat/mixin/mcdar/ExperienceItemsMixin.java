//? if mc-dungeons-artifacts {
package mod.crend.dynamiccrosshair.compat.mixin.mcdar;

import chronosacaria.mcdar.artifacts.LightningRodItem;
import chronosacaria.mcdar.artifacts.SatchelOfElementsItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = {
		LightningRodItem.class,
		SatchelOfElementsItem.class
}, remap = false)
public class ExperienceItemsMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getPlayer().totalExperience >= 15 || context.getPlayer().isCreative()) {
			return InteractionType.USE_ITEM;
		}
		return InteractionType.EMPTY;
	}
}
//?}
