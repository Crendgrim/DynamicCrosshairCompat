//? if farmers-delight-refabricated {
package mod.crend.dynamiccrosshair.compat.mixin.farmersdelight;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.CropBlock;
import org.spongepowered.asm.mixin.Mixin;
import vectorwing.farmersdelight.common.block.TomatoVineBlock;

@Mixin(value = TomatoVineBlock.class, remap = false)
public class TomatoVineBlockMixin extends CropBlock implements DynamicCrosshairBlock {
	public TomatoVineBlockMixin(Settings settings) {
		super(settings);
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (isMature(context.getBlockState())) {
			return InteractionType.TAKE_ITEM_FROM_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
