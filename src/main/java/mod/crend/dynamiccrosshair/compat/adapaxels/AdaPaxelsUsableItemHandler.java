package mod.crend.dynamiccrosshair.compat.adapaxels;

import com.brand.adapaxels.paxels.base.PaxelItem;
import mod.crend.dynamiccrosshair.api.IUsableItemHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.mixin.IAxeItemMixin;
import mod.crend.dynamiccrosshair.mixin.IShovelItemMixin;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.HoneycombItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

class AdaPaxelsUsableItemHandler implements IUsableItemHandler {
	@Override
	public Crosshair checkUsableItemOnBlock(ClientPlayerEntity player, ItemStack itemStack, BlockPos blockPos, BlockState blockState) {
		Item handItem = itemStack.getItem();
		if (handItem instanceof PaxelItem) {
			if (IAxeItemMixin.getSTRIPPED_BLOCKS().get(blockState.getBlock()) != null
					|| Oxidizable.getDecreasedOxidationBlock(blockState.getBlock()).isPresent()
					|| HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get().get(blockState.getBlock()) != null) {
				return Crosshair.USE_ITEM;
			}
			if (IShovelItemMixin.getPATH_STATES().get(blockState.getBlock()) != null) {
				return Crosshair.USE_ITEM;
			}
		}
		return null;
	}
}
