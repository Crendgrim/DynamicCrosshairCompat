package mod.crend.dynamiccrosshair.compat.patchouli;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IUsableItemHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.common.item.PatchouliItems;

public class ApiImplPatchouli implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return PatchouliAPI.MOD_ID;
	}

	IUsableItemHandler usableItemHandler = new PatchouliUsableItemHandler();

	@Override
	public IUsableItemHandler getUsableItemHandler() {
		return usableItemHandler;
	}

	private static class PatchouliUsableItemHandler implements IUsableItemHandler {
		@Override
		public boolean isUsableItem(ItemStack itemStack) {
			return itemStack.isOf(PatchouliItems.BOOK);
		}

		@Override
		public Crosshair checkUsableItem(ClientPlayerEntity player, ItemStack itemStack) {
			if (itemStack.isOf(PatchouliItems.BOOK)) {
				return Crosshair.USE_ITEM;
			}

			return null;
		}
	}
}
