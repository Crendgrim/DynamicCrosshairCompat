package mod.crend.dynamiccrosshair.compat.carrier;

import me.steven.carrier.Carrier;
import me.steven.carrier.api.CarriableRegistry;
import me.steven.carrier.items.GloveItem;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IEntityHandler;
import mod.crend.dynamiccrosshair.api.IUsableItemHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class ApiImplCarrier implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Carrier.MOD_ID;
	}

	@Override
	public IEntityHandler getEntityHandler() {
		return (player, itemStack, entity) -> {

			// FIXME apply only on main hand interaction
			if (itemStack.getItem() instanceof GloveItem) {
				if (CarriableRegistry.INSTANCE.contains(entity.getType())) {
					return Crosshair.USE_ITEM;
				}
			}

			return null;
		};
	}

	IUsableItemHandler usableItemHandler = new CarrierUsableItemHandler();

	@Override
	public IUsableItemHandler getUsableItemHandler() {
		return usableItemHandler;
	}

	private static class CarrierUsableItemHandler implements IUsableItemHandler {
		@Override
		public boolean isUsableItem(ItemStack itemStack) {
			return itemStack.getItem() instanceof GloveItem;
		}

		@Override
		public Crosshair checkUsableItemOnBlock(ClientPlayerEntity player, ItemStack itemStack, BlockPos blockPos, BlockState blockState) {
			// FIXME apply only on main hand interaction
			if (itemStack.getItem() instanceof GloveItem) {
				if (CarriableRegistry.INSTANCE.contains(blockState.getBlock()) && blockState.getHardness(MinecraftClient.getInstance().world, blockPos) > -1.0F) {
					return Crosshair.USE_ITEM;
				}
			}

			return null;
		}
	}
}
