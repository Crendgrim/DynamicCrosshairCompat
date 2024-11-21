//? if addadd {
package mod.crend.dynamiccrosshair.compat.mixin.additionaladditions;

import dqu.additionaladditions.item.WrenchItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.state.property.Properties;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = WrenchItem.class, remap = false)
public class WrenchItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock()) {
			BlockState blockState = context.getBlockState();
			Block block = blockState.getBlock();
			if (!(block instanceof ChestBlock || block instanceof BedBlock)) {
				if (blockState.contains(Properties.FACING)
						|| blockState.contains(Properties.HOPPER_FACING)
						|| blockState.contains(Properties.HORIZONTAL_FACING)
						|| blockState.contains(Properties.AXIS)
						|| blockState.contains(Properties.HORIZONTAL_AXIS)
				) {
					return InteractionType.USE_ITEM_ON_BLOCK;
				}
				if (block instanceof SlabBlock) {
					if (!(blockState.get(Properties.SLAB_TYPE)).equals(SlabType.DOUBLE)) {
						return InteractionType.USE_ITEM_ON_BLOCK;
					}
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
