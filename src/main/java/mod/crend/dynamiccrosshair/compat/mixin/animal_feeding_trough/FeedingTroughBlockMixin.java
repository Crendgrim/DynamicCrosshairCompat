//? if animal_feeding_trough {
package mod.crend.dynamiccrosshair.compat.mixin.animal_feeding_trough;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;
import slexom.animal_feeding_trough.platform.common.block.FeedingTroughBlock;

@Mixin(FeedingTroughBlock.class)
public class FeedingTroughBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
