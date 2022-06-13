package mod.crend.dynamiccrosshair.compat.enderchests;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IUsableItemHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import shetiphian.enderchests.EnderChests;
import shetiphian.enderchests.common.item.ItemEnderBag;
import shetiphian.enderchests.common.item.ItemEnderPouch;

public class ApiImplEnderChests implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return EnderChests.MOD_ID;
	}

	IUsableItemHandler usableItemHandler = new EnderChestsUsableItemHandler();

	@Override
	public IUsableItemHandler getUsableItemHandler() {
		return usableItemHandler;
	}

	private static class EnderChestsUsableItemHandler implements IUsableItemHandler {
		@Override
		public boolean isUsableItem(ItemStack itemStack) {
			return (itemStack.getItem() instanceof ItemEnderPouch || itemStack.getItem() instanceof ItemEnderBag);
		}

		@Override
		public Crosshair checkUsableItem(ClientPlayerEntity player, ItemStack itemStack) {
			if (isUsableItem(itemStack)) {
				return Crosshair.USE_ITEM;
			}

			return null;
		}
	}
}
