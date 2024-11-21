//? if techreborn {
package mod.crend.dynamiccrosshair.compat.mixin.techreborn;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;
import techreborn.items.tool.advanced.AdvancedJackhammerItem;
import techreborn.items.tool.industrial.IndustrialChainsawItem;
import techreborn.items.tool.industrial.IndustrialDrillItem;
import techreborn.items.tool.industrial.IndustrialJackhammerItem;
import techreborn.items.tool.industrial.NanosaberItem;

@Mixin(value = {
		AdvancedJackhammerItem.class,
		IndustrialChainsawItem.class,
		IndustrialDrillItem.class,
		IndustrialJackhammerItem.class,
		NanosaberItem.class
}, remap = false)
public class AlwaysUsableOnSneakingItemsMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return context.getPlayer().isSneaking() ? InteractionType.USE_ITEM : InteractionType.EMPTY;
	}
}
//?}
