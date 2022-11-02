package mod.crend.dynamiccrosshair.compat.ratsmischief;

import ladysnake.ratsmischief.common.Mischief;
import ladysnake.ratsmischief.common.entity.RatEntity;
import ladysnake.ratsmischief.common.item.RatPouchItem;
import ladysnake.ratsmischief.common.item.RatStaffItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ApiImplRatsMischief implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Mischief.MODID;
	}

	@Override
	public Crosshair checkEntity(CrosshairContext context) {
		if (context.getEntity() instanceof RatEntity rat) {
			ItemStack itemStack = context.getItemStack();
			Item item = itemStack.getItem();
			if (item instanceof DyeItem && rat.getRatType() == RatEntity.Type.RAT_KID) {
				return Crosshair.USE_ITEM;
			}
			if (rat.isTamed()) {
				if (rat.isBreedingItem(itemStack) && rat.getHealth() < rat.getMaxHealth()) {
					return Crosshair.USE_ITEM;
				}
				if (rat.isOwner(context.player)) {
					if (context.player.isSneaking()) {
						if (rat.isElytrat()) {
							return Crosshair.INTERACTABLE;
						}
						if (item == Mischief.ELYTRAT) {
							return Crosshair.USE_ITEM;
						}
					} else if (item instanceof RatPouchItem) {
						return Crosshair.USE_ITEM;
					} else if (!(item instanceof RatStaffItem)
							&& !rat.isBreedingItem(itemStack)
							&& (!(itemStack.getItem() instanceof DyeItem) || rat.getRatType() != RatEntity.Type.RAT_KID)
					) {
						// sit down / up rat
						return Crosshair.INTERACTABLE;
					}
				}
			} else if (item.isFood() && !rat.hasAngerTime()) {
				return Crosshair.USE_ITEM;
			}
		}
		return null;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof RatStaffItem;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof RatPouchItem;
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		if (context.getItem() instanceof RatPouchItem && context.player.isSneaking()) {
			return Crosshair.USE_ITEM;
		}

		return null;
	}
}
