//? if wraith-waystones {
package mod.crend.dynamiccrosshair.compat.mixin.fwaystones;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;
import wraith.fwaystones.item.AbyssWatcherItem;
import wraith.fwaystones.item.PocketWormholeItem;
import wraith.fwaystones.item.ScrollOfInfiniteKnowledgeItem;

@Mixin(value = {
		AbyssWatcherItem.class,
		PocketWormholeItem.class,
		ScrollOfInfiniteKnowledgeItem.class
}, remap = false)
public class AlwaysUsableItemsMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return InteractionType.USE_ITEM;
	}
}
//?}
