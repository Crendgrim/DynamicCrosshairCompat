package mod.crend.dynamiccrosshair.compat.carrier;

import me.steven.carrier.Carrier;
import me.steven.carrier.api.CarriableRegistry;
import me.steven.carrier.items.GloveItem;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IEntityHandler;
import mod.crend.dynamiccrosshair.api.IUsableItemHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class ApiImplCarrier implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Carrier.MOD_ID;
	}

	@Override
	public IEntityHandler getEntityHandler() {
		return context -> {
			if (context.getHand() == Hand.MAIN_HAND && context.getItem() instanceof GloveItem) {
				if (CarriableRegistry.INSTANCE.contains(context.getEntity().getType())) {
					return Crosshair.USE_ITEM;
				}
			}

			return null;
		};
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof GloveItem;
	}

	@Override
	public IUsableItemHandler getUsableItemHandler() {
		return context -> {
			if (context.isWithBlock() && context.getHand() == Hand.MAIN_HAND) {
				if (context.getItem() instanceof GloveItem) {
					BlockState blockState = context.getBlockState();
					if (CarriableRegistry.INSTANCE.contains(blockState.getBlock()) && blockState.getHardness(context.world, context.getBlockPos()) > -1.0F) {
						return Crosshair.USE_ITEM;
					}
				}
			}

			return null;
		};
	}
}
