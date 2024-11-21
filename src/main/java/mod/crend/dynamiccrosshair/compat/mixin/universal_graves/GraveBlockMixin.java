//? if universal-graves {
package mod.crend.dynamiccrosshair.compat.mixin.universal_graves;

import eu.pb4.graves.config.ConfigManager;
import eu.pb4.graves.registry.GraveBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = GraveBlock.class, remap = false)
public class GraveBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isMainHand()) {
			if (!ConfigManager.getConfig().interactions.shiftClickTakesItems || !context.getPlayer().isSneaking() && ConfigManager.getConfig().interactions.clickGraveToOpenGui) {
				return InteractionType.INTERACT_WITH_BLOCK;
			} else {
				return InteractionType.PLACE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
