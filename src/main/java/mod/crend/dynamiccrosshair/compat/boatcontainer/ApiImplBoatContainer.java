package mod.crend.dynamiccrosshair.compat.boatcontainer;

import de.kxmischesdomi.boatcontainer.BoatContainer;
import de.kxmischesdomi.boatcontainer.common.entity.EnderChestBoatEntity;
import de.kxmischesdomi.boatcontainer.common.entity.FurnaceBoatEntity;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.mixin.boatcontainer.IBoatWithBlockEntityMixin;
import mod.crend.dynamiccrosshair.compat.mixin.boatcontainer.IFurnaceBoatEntityMixin;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.entity.Entity;

public class ApiImplBoatContainer implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return BoatContainer.MOD_ID;
	}

	@Override
	public Crosshair checkEntity(CrosshairContext context) {

		Entity entity = context.getEntity();

		if (entity instanceof EnderChestBoatEntity enderChestBoatEntity) {
			if (context.player.shouldCancelInteraction() || ((IBoatWithBlockEntityMixin) enderChestBoatEntity).invokeCanAddPassenger(context.player)) {
				return Crosshair.INTERACTABLE;
			}
		}

		if (entity instanceof FurnaceBoatEntity furnaceBoatEntity) {
			if (IFurnaceBoatEntityMixin.getINGREDIENT().test(context.getItemStack()) && ((IFurnaceBoatEntityMixin) furnaceBoatEntity).getFuel() + 3600 <= 32000) {
				return Crosshair.USE_ITEM;
			}
			if (!context.player.shouldCancelInteraction()) {
				return Crosshair.INTERACTABLE;
			}
		}

		return null;
	}
}
