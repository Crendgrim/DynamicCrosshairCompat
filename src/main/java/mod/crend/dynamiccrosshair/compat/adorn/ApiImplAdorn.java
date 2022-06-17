package mod.crend.dynamiccrosshair.compat.adorn;

import juuxel.adorn.block.*;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.mixin.adorn.ISeatBlockMixin;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class ApiImplAdorn implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "adorn";
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		Block block = context.getBlock();
		Item handItem = context.getItem();

		if (block instanceof SeatBlock seatBlock && ((ISeatBlockMixin) seatBlock).invokeIsSittingEnabled()) {
			return Crosshair.INTERACTABLE;
		}
		if (block instanceof TableLampBlock || block instanceof KitchenCupboardBlock || block instanceof DrawerBlock) {
			return Crosshair.INTERACTABLE;
		}

		if (block instanceof KitchenSinkBlock) {
			if (context.getItemStack().isOf(Items.SPONGE) || handItem instanceof BucketItem) {
				return Crosshair.USE_ITEM;
			}
		}

		return null;
	}
}
