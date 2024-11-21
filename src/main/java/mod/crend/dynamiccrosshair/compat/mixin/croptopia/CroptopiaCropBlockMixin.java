//? if croptopia {
package mod.crend.dynamiccrosshair.compat.mixin.croptopia;

import com.epherical.croptopia.CroptopiaMod;
import com.epherical.croptopia.blocks.CroptopiaCropBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = CroptopiaCropBlock.class, remap = false)
public class CroptopiaCropBlockMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		if (block instanceof CroptopiaCropBlock cropBlock
				&& !CroptopiaMod.getInstance().platform().skipHarvest()
				&& blockState.get(cropBlock.getAgeProperty()) == cropBlock.getMaxAge()
		) {
			return InteractionType.TAKE_ITEM_FROM_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
