//? if chimes {
package mod.crend.dynamiccrosshair.compat.mixin.chimes;

import com.nick.chimes.block.WindChimeBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = WindChimeBlock.class, remap = false)
public class WindChimeBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (!(context.getItem() instanceof AxeItem)) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
