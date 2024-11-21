//? if mcdar {
package mod.crend.dynamiccrosshair.compat.mixin.mcdar;

import chronosacaria.mcdar.artifacts.ArtifactSummoningItem;
import chronosacaria.mcdar.artifacts.TotemOfRegenerationItem;
import chronosacaria.mcdar.artifacts.TotemOfShieldingItem;
import chronosacaria.mcdar.artifacts.TotemOfSoulProtectionItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = {
		ArtifactSummoningItem.class,
		TotemOfShieldingItem.class,
		TotemOfRegenerationItem.class,
		TotemOfSoulProtectionItem.class
}, remap = false)
public class AlwaysUsableOnBlockItemsMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return context.isWithBlock() ? InteractionType.USE_ITEM_ON_BLOCK : InteractionType.EMPTY;
	}
}
//?}
