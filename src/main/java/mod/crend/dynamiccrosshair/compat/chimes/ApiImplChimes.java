package mod.crend.dynamiccrosshair.compat.chimes;

import com.nick.chimes.Chimes;
import com.nick.chimes.block.WindChimeBlock;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;

public class ApiImplChimes implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Chimes.modid;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof WindChimeBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack itemStack = context.getItemStack();

		if (block instanceof WindChimeBlock) {
			if (!(itemStack.getItem() instanceof AxeItem)) {
				return Crosshair.INTERACTABLE;
			}
		}

		return null;
	}
}
