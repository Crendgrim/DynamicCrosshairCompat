package mod.crend.dynamiccrosshair.compat.rings;

import com.kwpugh.ring_of_attraction.RingOfAttraction;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.ItemStack;

public class ApiImplRingOfAttraction implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return RingOfAttraction.MOD_ID;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return itemStack.isOf(RingOfAttraction.RING_OF_ATTRACTION);
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		if (context.getItemStack().isOf(RingOfAttraction.RING_OF_ATTRACTION) && !context.player.shouldCancelInteraction()) {
			return Crosshair.USE_ITEM;
		}

		return null;
	}
}
