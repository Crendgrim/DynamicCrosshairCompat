package mod.crend.dynamiccrosshair.compat.akashictomeoftools;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IUsableItemHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import vazkii.akashictomeoftools.AkashicTome;

public class ApiImplAkashicTomeOfTools implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "akashictomeoftools";
	}

	IUsableItemHandler usableItemHandler = new AkashicTomeOfToolsUsableItemHandler();

	@Override
	public IUsableItemHandler getUsableItemHandler() {
		return usableItemHandler;
	}

	private static class AkashicTomeOfToolsUsableItemHandler implements IUsableItemHandler {
		@Override
		public boolean isUsableItem(ItemStack itemStack) {
			return (itemStack.isOf(AkashicTome.TOME_ITEM));
		}

		@Override
		public Crosshair checkUsableItem(ClientPlayerEntity player, ItemStack itemStack) {
			if (itemStack.isOf(AkashicTome.TOME_ITEM)) {
				return Crosshair.USE_ITEM;
			}

			return null;
		}
	}
}
