//? if adventurez {
package mod.crend.dynamiccrosshair.compat.mixin.adventurez;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;
//? if =1.20.1 {
import net.adventurez.init.ConfigInit;
import net.adventurez.item.GildedBlackstoneShard;
//?} else
/*import net.adventurez.item.GildedStoneItem;*/

@Mixin(value = /*? if =1.20.1 {*/GildedBlackstoneShard/*?} else {*//*GildedStoneItem*//*?}*/.class, remap = false)
public class GildedBlackstoneShardMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (!context.getPlayer().isSneaking()
				//? if =1.20.1
				&& ConfigInit.CONFIG.allow_gilded_blackstone_shard_throw
		) {
			return InteractionType.THROW_ITEM;
		}
		return InteractionType.EMPTY;
	}
}
//?}
