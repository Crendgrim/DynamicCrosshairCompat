package mod.crend.dynamiccrosshair.compat.botania;

import mod.crend.dynamiccrosshair.DynamicCrosshair;
import mod.crend.dynamiccrosshair.api.IRangedWeaponHandler;
import mod.crend.dynamiccrosshair.api.IThrowableItemHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.config.RangedCrosshairPolicy;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import vazkii.botania.common.item.ItemManaGun;
import vazkii.botania.common.item.ItemSlingshot;
import vazkii.botania.common.item.ItemThornChakram;
import vazkii.botania.common.item.material.ItemEnderAir;
import vazkii.botania.common.item.rod.ItemMissileRod;

class BotaniaItemHandler implements IThrowableItemHandler, IRangedWeaponHandler {

	@Override
	public Crosshair checkThrowable(ClientPlayerEntity player, ItemStack itemStack) {
		Item item = itemStack.getItem();
		if (item instanceof ItemEnderAir || item instanceof ItemThornChakram) {
			return Crosshair.THROWABLE;
		}

		return null;
	}

	@Override
	public Crosshair checkRangedWeapon(ClientPlayerEntity player, ItemStack itemStack) {
		Item item = itemStack.getItem();
		if (item instanceof ItemMissileRod) {
			return Crosshair.RANGED_WEAPON;
		}
		if (DynamicCrosshair.config.dynamicCrosshairHoldingRangedWeapon() == RangedCrosshairPolicy.Always) {
			if (item instanceof ItemManaGun || item instanceof ItemSlingshot) {
				return Crosshair.RANGED_WEAPON;
			}
			return null;
		}
		if (item instanceof ItemManaGun manaGun) {
			if (manaGun.isItemBarVisible(itemStack)) {
				return Crosshair.REGULAR;
			}
			return Crosshair.RANGED_WEAPON;
		}
		if (item instanceof ItemSlingshot) {
			if (player.getActiveItem().equals(itemStack)) {
				float progress = BowItem.getPullProgress(item.getMaxUseTime(itemStack) - player.getItemUseTimeLeft());
				if (progress == 1.0f) {
					return Crosshair.RANGED_WEAPON;
				}
			}
			return Crosshair.REGULAR;
		}
		return null;
	}
}
