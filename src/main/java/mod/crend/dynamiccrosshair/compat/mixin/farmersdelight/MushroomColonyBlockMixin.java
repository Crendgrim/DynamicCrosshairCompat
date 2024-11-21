//? if farmers-delight-refabricated {
package mod.crend.dynamiccrosshair.compat.mixin.farmersdelight;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.ShearsItem;
import org.spongepowered.asm.mixin.Mixin;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;

@Mixin(value = MushroomColonyBlock.class, remap = false)
public class MushroomColonyBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockState().get(MushroomColonyBlock.COLONY_AGE) > 0 && context.getItem() instanceof ShearsItem) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
