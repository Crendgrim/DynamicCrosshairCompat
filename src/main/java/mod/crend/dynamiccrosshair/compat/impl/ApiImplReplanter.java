package mod.crend.dynamiccrosshair.compat.impl;

import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CocoaBlock;
import net.minecraft.block.CropBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class ApiImplReplanter implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "replanter";
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack itemStack = context.getItemStack();

		if (itemStack.getItem() instanceof BlockItem blockItem) {
			if (block instanceof CropBlock) {
				if (blockItem.getBlock() instanceof CropBlock) {
					return new Crosshair(InteractionType.USE_ITEM_ON_BLOCK);
				}
			} else if (block instanceof NetherWartBlock && block == blockItem.getBlock()) {
				if (blockState.get(NetherWartBlock.AGE) == 3) {
					return new Crosshair(InteractionType.USE_ITEM_ON_BLOCK);
				}
			} else if (block instanceof CocoaBlock) {
				if (blockState.get(CocoaBlock.AGE) == CocoaBlock.MAX_AGE) {
					return new Crosshair(InteractionType.USE_ITEM_ON_BLOCK);
				}
			}
		}

		return null;
	}
}
