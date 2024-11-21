package mod.crend.dynamiccrosshair.compat.impl;

//? if more-banner-features {
import de.kxmischesdomi.morebannerfeatures.core.accessor.Bannerable;
import de.kxmischesdomi.morebannerfeatures.core.accessor.InventoryBannerable;
//?}
import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BannerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ShearsItem;


public class ApiImplMoreBannerFeatures implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "morebannerfeatures";
	}

	//? if more-banner-features {
	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Item item = context.getItem();
		Entity entity = context.getEntity();

		if (entity instanceof Bannerable bannerable && !(entity instanceof InventoryBannerable || entity instanceof PlayerEntity)) {
			if (bannerable.getBannerItem().isEmpty()) {
				if (item instanceof BannerItem) {
					return new Crosshair(InteractionType.USE_ITEM_ON_ENTITY);
				}
			} else {
				if (item instanceof ShearsItem) {
					return new Crosshair(InteractionType.USE_ITEM_ON_ENTITY);
				}
			}
		}

		return null;
	}
	//?}
}
