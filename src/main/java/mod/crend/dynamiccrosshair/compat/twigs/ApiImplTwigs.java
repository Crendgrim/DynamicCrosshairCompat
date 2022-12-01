package mod.crend.dynamiccrosshair.compat.twigs;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.moddingplayground.twigs.api.Twigs;
import net.moddingplayground.twigs.api.block.TwigsBlocks;

public class ApiImplTwigs implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Twigs.MOD_ID;
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		if (itemStack.getItem() instanceof ShearsItem && TwigsBlocks.MAP_AZALEA_SHEARING.containsKey(block)) {
			return Crosshair.USABLE;
		} else if (itemStack.getItem() instanceof AxeItem
				&& blockState.isOf(Blocks.BAMBOO)
				&& !context.world.getBlockState(context.getBlockPos().up()).isOf(Blocks.BAMBOO)) {
			return Crosshair.USABLE;
		}

		return null;
	}
}
