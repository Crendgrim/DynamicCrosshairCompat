package mod.crend.dynamiccrosshair.compat.morebannerfeatures;

import de.kxmischesdomi.morebannerfeatures.MoreBannerFeatures;
import de.kxmischesdomi.morebannerfeatures.core.accessor.Bannerable;
import de.kxmischesdomi.morebannerfeatures.core.accessor.InventoryBannerable;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BannerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ShearsItem;

public class ApiImplMoreBannerFeatures implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return MoreBannerFeatures.MOD_ID;
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair checkEntity(CrosshairContext context) {
		Item item = context.getItem();
		Entity entity = context.getEntity();

		if (entity instanceof Bannerable bannerable && !(entity instanceof InventoryBannerable || entity instanceof PlayerEntity)) {
			if (bannerable.getBannerItem().isEmpty()) {
				if (item instanceof BannerItem) {
					return Crosshair.USE_ITEM;
				}
			} else {
				if (item instanceof ShearsItem) {
					return Crosshair.USE_ITEM;
				}
			}
		}

		return null;
	}
}
