//? if adventurez {
package mod.crend.dynamiccrosshair.compat.mixin.adventurez;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
//? if =1.20.1 {
import net.adventurez.item.BlackstoneGolemHeart;
//?} else
/*import net.adventurez.item.StoneGolemHeartItem;*/
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = /*? if =1.20.1 {*/BlackstoneGolemHeart/*?} else {*//*StoneGolemHeartItem*//*?}*/.class, remap = false)
public class BlackstoneGolemHeartMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getPlayer().isSneaking()) {
			return InteractionType.USE_ITEM;
		}
		return InteractionType.EMPTY;
	}
}
//?}
