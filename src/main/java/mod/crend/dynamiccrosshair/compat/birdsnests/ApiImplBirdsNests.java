package mod.crend.dynamiccrosshair.compat.birdsnests;

import daniking.birdsnests.BirdsNests;
import daniking.birdsnests.NestItem;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IUsableItemHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;

public class ApiImplBirdsNests implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return BirdsNests.MODID;
	}

	IUsableItemHandler usableItemHandler = new BirdsNestsUsableItemHandler();

	@Override
	public IUsableItemHandler getUsableItemHandler() {
		return usableItemHandler;
	}

	private static class BirdsNestsUsableItemHandler implements IUsableItemHandler {
		@Override
		public boolean isUsableItem(ItemStack itemStack) {
			return itemStack.getItem() instanceof NestItem;
		}

		@Override
		public Crosshair checkUsableItem(ClientPlayerEntity player, ItemStack itemStack) {

			if (itemStack.getItem() instanceof NestItem) {
				return Crosshair.USE_ITEM;
			}

			return null;
		}
	}
}
