//? if mc-dungeons-artifacts {
package mod.crend.dynamiccrosshair.compat.mixin.mcdar;

import chronosacaria.mcdar.artifacts.ArtifactAgilityItem;
import chronosacaria.mcdar.artifacts.ArtifactDefensiveItem;
import chronosacaria.mcdar.artifacts.ArtifactQuiverItem;
import chronosacaria.mcdar.artifacts.ArtifactStatusInflictingItem;
import chronosacaria.mcdar.artifacts.BlastFungusItem;
import chronosacaria.mcdar.artifacts.UpdraftTomeItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = {
		ArtifactAgilityItem.class,
		ArtifactDefensiveItem.class,
		ArtifactQuiverItem.class,
		BlastFungusItem.class,
		UpdraftTomeItem.class,
		ArtifactStatusInflictingItem.class
}, remap = false)
public class AlwaysUsableItemsMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return InteractionType.USE_ITEM;
	}
}
//?}
