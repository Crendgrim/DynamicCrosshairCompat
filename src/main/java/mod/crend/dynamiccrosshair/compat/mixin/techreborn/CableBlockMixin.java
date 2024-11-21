//? if techreborn {
package mod.crend.dynamiccrosshair.compat.mixin.techreborn;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import reborncore.api.ToolManager;
import techreborn.blocks.cable.CableBlock;
import techreborn.init.TRContent;

@Mixin(value = CableBlock.class, remap = false)
public class CableBlockMixin implements DynamicCrosshairBlock {
	@Shadow @Final public TRContent.Cables type;

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (ToolManager.INSTANCE.canHandleTool(itemStack)) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		if (!itemStack.isEmpty()) {
			BlockState blockState = context.getBlockState();
			if (!blockState.get(CableBlock.COVERED) && !this.type.canKill && itemStack.getItem() == TRContent.Plates.WOOD.asItem()) {
				return InteractionType.PLACE_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
