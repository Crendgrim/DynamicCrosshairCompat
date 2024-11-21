//? if paladins-furniture {
package mod.crend.dynamiccrosshair.compat.mixin.pfm;

import com.unlikepaladin.pfm.blocks.CutleryBlock;
import com.unlikepaladin.pfm.blocks.PlateBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = CutleryBlock.class, remap = false)
public class CutleryBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Block handBlock = Registries.BLOCK.get(Registries.ITEM.getId(itemStack.getItem()));
		if (handBlock instanceof PlateBlock) {
			return InteractionType.PLACE_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
