//? if another-furniture {
package mod.crend.dynamiccrosshair.compat.mixin.another_furniture;

import com.starfish_studios.another_furniture.block.ServiceBellBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ServiceBellBlock.class, remap = false)
public class ServiceBellBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (!context.getBlockState().get(ServiceBellBlock.POWERED)) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
