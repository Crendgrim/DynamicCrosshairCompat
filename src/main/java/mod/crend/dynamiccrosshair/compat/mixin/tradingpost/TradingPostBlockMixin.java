//? if trading-post {
package mod.crend.dynamiccrosshair.compat.mixin.tradingpost;

import fuzs.tradingpost.world.level.block.TradingPostBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = TradingPostBlock.class, remap = false)
public class TradingPostBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext crosshairContext) {
		return InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
