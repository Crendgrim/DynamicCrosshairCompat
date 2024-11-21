//? if techreborn {
package mod.crend.dynamiccrosshair.compat.mixin.techreborn;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import techreborn.blocks.cable.CableBlock;
import techreborn.items.tool.PaintingToolItem;

@Mixin(value = PaintingToolItem.class, remap = false)
public class PaintingToolItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock()) {
			BlockState blockState = context.getBlockState();
			if (context.getPlayer().isSneaking()) {
				if (blockState.isOpaqueFullCube(context.getWorld(), context.getBlockPos()) && blockState.getBlock().getDefaultState().isOpaqueFullCube(context.getWorld(), context.getBlockPos())) {
					return InteractionType.USE_ITEM_ON_BLOCK;
				}
			} else {
				BlockState cover = PaintingToolItem.getCover(context.getWorld(), context.getItemStack());
				if (cover != null && blockState.getBlock() instanceof CableBlock && blockState.get(CableBlock.COVERED)) {
					return InteractionType.USE_ITEM_ON_BLOCK;
				}
			}

		}
		return InteractionType.EMPTY;
	}
}
//?}
