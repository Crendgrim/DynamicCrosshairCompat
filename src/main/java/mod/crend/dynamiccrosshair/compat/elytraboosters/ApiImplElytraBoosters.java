package mod.crend.dynamiccrosshair.compat.elytraboosters;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.adriantodt.elytraboosters.item.BoosterItem;
import net.adriantodt.elytraboosters.item.ForwardLauncherItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ApiImplElytraBoosters implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "elytraboosters";
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof BoosterItem || itemStack.getItem() instanceof ForwardLauncherItem;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (!context.includeUsableItem()) return null;

		ItemStack handItem = context.getItemStack();
		Item item = handItem.getItem();

		if (item instanceof BoosterItem) {
			if (handItem.hasNbt() && handItem.getNbt().getBoolean("Active")) {
				return Crosshair.USABLE;
			}
			if (context.player.isFallFlying()) {
				return Crosshair.USABLE;
			}
		}

		if (item instanceof ForwardLauncherItem) {
			if (context.player.isOnGround()) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
