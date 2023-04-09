package mod.crend.dynamiccrosshair.compat.architectspalette;

import com.slomaxonical.architectspalette.ArchitectsPalette;
import com.slomaxonical.architectspalette.blocks.CageLanternBlock;
import com.slomaxonical.architectspalette.blocks.TotemBlock;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;

public class ApiImplArchitectsPalette implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return ArchitectsPalette.MOD_ID;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof CageLanternBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack itemStack = context.getItemStack();

		if (block instanceof TotemBlock) {
			if (itemStack.getItem() instanceof AxeItem) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
