package mod.crend.dynamiccrosshair.compat.minecarttweaks;

import dev.cammiescorner.cammiesminecarttweaks.MinecartTweaks;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IEntityHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ApiImplMinecartTweaks implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return MinecartTweaks.MOD_ID;
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public IEntityHandler getEntityHandler() {
		return context -> {
			if (context.getEntity() instanceof AbstractMinecartEntity minecart) {
				ItemStack itemStack = context.getItemStack();
				if (MinecartTweaks.getConfig().serverTweaks.canLinkMinecarts) {
					if (context.player.isSneaking() && itemStack.isOf(Items.CHAIN)) {
						return Crosshair.USE_ITEM;
					}
				}
				if (minecart.getMinecartType() == AbstractMinecartEntity.Type.RIDEABLE) {
					if (itemStack.isOf(Items.FURNACE) || itemStack.isOf(Items.CHEST)
							|| itemStack.isOf(Items.TNT) || itemStack.isOf(Items.HOPPER)) {
						return Crosshair.USE_ITEM;
					}
				}
			}

			return null;
		};
	}
}
