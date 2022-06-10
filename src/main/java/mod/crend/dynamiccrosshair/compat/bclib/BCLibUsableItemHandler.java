package mod.crend.dynamiccrosshair.compat.bclib;

import mod.crend.dynamiccrosshair.api.IUsableItemHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import ru.bclib.blocks.BaseStripableLogBlock;
import ru.bclib.blocks.StripableBarkBlock;

public class BCLibUsableItemHandler implements IUsableItemHandler {
	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return false;
	}

	@Override
	public Crosshair checkUsableItem(ClientPlayerEntity clientPlayerEntity, ItemStack itemStack) {
		return null;
	}

	@Override
	public Crosshair checkUsableItemOnBlock(ClientPlayerEntity clientPlayerEntity, ItemStack itemStack, BlockPos blockPos, BlockState blockState) {
		Item handItem = itemStack.getItem();
		Block block = blockState.getBlock();
		if (handItem instanceof AxeItem) {
			if (block instanceof BaseStripableLogBlock || block instanceof StripableBarkBlock) {
				return Crosshair.USE_ITEM;
			}
		}
		return null;
	}

	@Override
	public Crosshair checkUsableItemOnMiss(ClientPlayerEntity clientPlayerEntity, ItemStack itemStack) {
		return null;
	}
}
