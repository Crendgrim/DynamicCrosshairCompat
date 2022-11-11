package mod.crend.dynamiccrosshair.compat.numismaticoverhaul;

import com.glisco.numismaticoverhaul.NumismaticOverhaul;
import com.glisco.numismaticoverhaul.block.ShopBlock;
import com.glisco.numismaticoverhaul.item.CoinItem;
import com.glisco.numismaticoverhaul.item.MoneyBagItem;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ApiImplNumismaticOverhaul implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return NumismaticOverhaul.MOD_ID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (item instanceof CoinItem || item instanceof MoneyBagItem);
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof ShopBlock;
	}
}
