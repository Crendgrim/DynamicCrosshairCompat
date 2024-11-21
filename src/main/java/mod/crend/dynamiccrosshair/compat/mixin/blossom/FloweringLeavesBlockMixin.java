//? if blossom {
package mod.crend.dynamiccrosshair.compat.mixin.blossom;

import dev.yurisuika.blossom.block.FloweringLeavesBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.ShearsItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = FloweringLeavesBlock.class, remap = false)
public class FloweringLeavesBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItem() instanceof ShearsItem  && context.getBlockState().get(FloweringLeavesBlock.AGE) == 7) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
