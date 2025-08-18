package mod.crend.dynamiccrosshair.compat.impl;

//? if more-banner-features && <1.21.6 {
import de.kxmischesdomi.morebannerfeatures.core.accessor.Bannerable;
import de.kxmischesdomi.morebannerfeatures.core.accessor.InventoryBannerable;
//?} else if more-banner-features {
/*import dev.svrt.domiirl.mbf.accessor.Bannerable;
*///?}
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

		if (entity instanceof Bannerable bannerable
				/*? if <1.21.6 {*/
				&& !(entity instanceof InventoryBannerable || entity instanceof PlayerEntity)
				/*?} else {*/
				/*&& bannerable.mbf$isEnabled()
				*//*?}*/
		) {
			if (bannerable./*? if <1.21.6 {*/getBannerItem()/*?} else {*//*mbf$getBannerItem()*//*?}*/.isEmpty()) {
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
