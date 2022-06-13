package mod.crend.dynamiccrosshair.compat.macaw;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IUsableItemHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.kikoz.mcwdoors.MacawsDoors;
import net.kikoz.mcwdoors.objects.GarageDoor;
import net.kikoz.mcwdoors.objects.GarageRemote;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class ApiImplMacawsDoors implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return MacawsDoors.MOD_ID;
	}

	McwDoorsUsableItemHandler usableItemHandler = new McwDoorsUsableItemHandler();

	@Override
	public IUsableItemHandler getUsableItemHandler() {
		return usableItemHandler;
	}

	private static class McwDoorsUsableItemHandler implements IUsableItemHandler {
		@Override
		public boolean isUsableItem(ItemStack itemStack) {
			return (itemStack.getItem() instanceof GarageRemote);
		}

		@Override
		public Crosshair checkUsableItemOnBlock(ClientPlayerEntity player, ItemStack itemStack, BlockPos blockPos, BlockState blockState) {
			if (itemStack.getItem() instanceof GarageRemote) {
				if (blockState.getBlock() instanceof GarageDoor && !player.shouldCancelInteraction()) {
					return Crosshair.USE_ITEM;
				}
			}

			return null;
		}
	}
}
