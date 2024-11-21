//? if travelers_backpack {
package mod.crend.dynamiccrosshair.compat.mixin.travelersbackpack;

import com.tiviacz.travelersbackpack.blocks.TravelersBackpackBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = TravelersBackpackBlock.class, remap = false)
public class TravelersBackpackBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext crosshairContext) {
		return InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
