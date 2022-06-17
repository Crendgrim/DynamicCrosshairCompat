package mod.crend.dynamiccrosshair.compat.travelersbackpack;

import com.tiviacz.travelersbackpack.TravelersBackpack;
import com.tiviacz.travelersbackpack.blocks.TravelersBackpackBlock;
import com.tiviacz.travelersbackpack.items.TravelersBackpackItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.ItemStack;

public class ApiImplTravelersBackpack implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return TravelersBackpack.MODID;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		if (context.getBlock() instanceof TravelersBackpackBlock) {
			return Crosshair.INTERACTABLE;
		}
		return null;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return DynamicCrosshairApi.super.isUsableItem(itemStack);
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		if (!context.player.isSneaking() && context.getItem() instanceof TravelersBackpackItem) {
			return Crosshair.USE_ITEM;
		}

		return null;
	}
}
