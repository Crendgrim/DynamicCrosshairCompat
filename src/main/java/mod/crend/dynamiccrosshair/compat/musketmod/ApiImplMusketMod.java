package mod.crend.dynamiccrosshair.compat.musketmod;

import ewewukek.musketmod.GunItem;
import ewewukek.musketmod.MusketMod;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Hand;

public class ApiImplMusketMod implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return MusketMod.MODID;
	}

	@Override
	public Crosshair checkRangedWeapon(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();

		if (itemStack.getItem() instanceof GunItem gun
				&& gun.canUseFrom(context.player, context.getHand())
				&& (!context.player.isSubmergedIn(FluidTags.WATER) || context.player.getAbilities().creativeMode)
		) {
			if (context.isMainHand() && !gun.twoHanded() && GunItem.isLoaded(itemStack)) {
				ItemStack offhandStack = context.getItemStack(Hand.OFF_HAND);
				if (!offhandStack.isEmpty() && offhandStack.getItem() instanceof GunItem offhandGun) {
					if (!offhandGun.twoHanded() && GunItem.isLoaded(offhandStack)) {
						return null;
					}
				}
			}
			if (GunItem.isLoaded(itemStack)) {
				return Crosshair.RANGED_WEAPON;
			}
			if (!GunItem.findAmmo(context.player).isEmpty() || context.player.getAbilities().creativeMode) {
				return Crosshair.USE_ITEM;
			}
		}

		return null;
	}
}