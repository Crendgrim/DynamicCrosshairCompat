//? if paladins-furniture {
package mod.crend.dynamiccrosshair.compat.mixin.pfm;

import com.unlikepaladin.pfm.blocks.CutleryBlock;
import com.unlikepaladin.pfm.blocks.PlateBlock;
import com.unlikepaladin.pfm.blocks.blockentities.PlateBlockEntity;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = PlateBlock.class, remap = false)
public class PlateBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (!context.getBlockState().get(PlateBlock.CUTLERY)) {
			Block handBlock = Registries.BLOCK.get(Registries.ITEM.getId(itemStack.getItem()));
			if (handBlock instanceof CutleryBlock) {
				return InteractionType.PLACE_BLOCK;
			}
		}
		if (context.getBlockEntity() instanceof PlateBlockEntity plateBlockEntity) {
			if (itemStack.isFood()) {
				if (plateBlockEntity.getItemInPlate().isEmpty()) {
					return InteractionType.PLACE_ITEM_ON_BLOCK;
				}
			} else if (!plateBlockEntity.getItemInPlate().isEmpty()) {
				if (context.getPlayer().isSneaking()) {
					return InteractionType.TAKE_ITEM_FROM_BLOCK;
				} else {
					return InteractionType.USE_BLOCK;
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
