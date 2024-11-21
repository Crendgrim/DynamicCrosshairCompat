//? if adventurez {
package mod.crend.dynamiccrosshair.compat.mixin.adventurez;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.adventurez.init.ConfigInit;
import net.adventurez.item.GildedBlackstoneShard;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = GildedBlackstoneShard.class, remap = false)
public class GildedBlackstoneShardMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext crosshairContext) {
		if (ConfigInit.CONFIG.allow_gilded_blackstone_shard_throw) {
			return InteractionType.THROW_ITEM;
		}
		return InteractionType.EMPTY;
	}
}
//?}
