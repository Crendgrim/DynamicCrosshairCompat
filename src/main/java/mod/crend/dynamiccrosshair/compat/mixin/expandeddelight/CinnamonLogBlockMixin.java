//? if expanded-delight {
package mod.crend.dynamiccrosshair.compat.mixin.expandeddelight;

import com.ianm1647.expandeddelight.block.custom.CinnamonLogBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = CinnamonLogBlock.class, remap = false)
public class CinnamonLogBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItem() instanceof AxeItem) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
