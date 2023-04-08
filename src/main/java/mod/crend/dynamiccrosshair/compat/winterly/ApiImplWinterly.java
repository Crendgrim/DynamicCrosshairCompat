package mod.crend.dynamiccrosshair.compat.winterly;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import winterly.Winterly;
import winterly.block.GiftBoxBlock;
import winterly.block.entity.GiftBoxBlockEntity;

public class ApiImplWinterly implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Winterly.MOD_ID;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof GiftBoxBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack itemStack = context.getItemStack();

		if (block instanceof GiftBoxBlock && context.getBlockEntity() instanceof GiftBoxBlockEntity giftBoxBlockEntity) {
			if (!itemStack.isEmpty() && giftBoxBlockEntity.stacks.size() < Winterly.config.getGiftBoxCapacity()) {
				if (itemStack.getItem() instanceof BlockItem blockItem) {
					if (blockItem.getBlock() instanceof ShulkerBoxBlock || blockItem.getBlock() instanceof GiftBoxBlock) {
						return null;
					}
				}
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
