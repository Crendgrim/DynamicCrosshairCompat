package mod.crend.dynamiccrosshair.compat.transportables;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.ItemCategory;
import mod.crend.dynamiccrosshair.compat.mixin.transportables.EntityAccessor;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.thegrimsey.transportables.Transportables;
import net.thegrimsey.transportables.entity.AbstractCarriageEntity;
import net.thegrimsey.transportables.entity.ChestCarriageEntity;
import net.thegrimsey.transportables.items.CarriageItem;
import net.thegrimsey.transportables.items.ChestCarriageItem;
import net.thegrimsey.transportables.items.LinkerItem;

public class ApiImplGrimsTransportables implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Transportables.MODID;
	}

	@Override
	public ItemCategory getItemCategory(ItemStack itemStack) {
		if (itemStack.getItem() instanceof CarriageItem || itemStack.getItem() instanceof ChestCarriageItem) {
			return ItemCategory.BLOCK;
		}
		return DynamicCrosshairApi.super.getItemCategory(itemStack);
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof LinkerItem;
	}

	@Override
	public boolean isInteractableEntity(Entity entity) {
		return entity instanceof AbstractCarriageEntity;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();
		if (entity instanceof AbstractCarriageEntity) {
			if ((((EntityAccessor) context.player).invokeCanStartRiding(entity) && ((EntityAccessor) entity).invokeCanAddPassenger(context.player))) {
				return Crosshair.INTERACTABLE;
			}
		}
		if (entity instanceof ChestCarriageEntity) {
			if (context.player.isSneaking()) {
				return Crosshair.INTERACTABLE;
			}
		}
		return null;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		return GrimsTransportablesHandler.computeFromItem(context);
	}
}
