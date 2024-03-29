package mod.crend.dynamiccrosshair.compat.boatcontainer;

import de.kxmischesdomi.boatcontainer.BoatContainer;
import de.kxmischesdomi.boatcontainer.common.entity.EnderChestBoatEntity;
import de.kxmischesdomi.boatcontainer.common.entity.FurnaceBoatEntity;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.mixin.boatcontainer.BoatWithBlockEntityAccessor;
import mod.crend.dynamiccrosshair.compat.mixin.boatcontainer.FurnaceBoatEntityAccessor;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.entity.Entity;

public class ApiImplBoatContainer implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return BoatContainer.MOD_ID;
	}

	@Override
	public boolean isInteractableEntity(Entity entity) {
		return entity instanceof EnderChestBoatEntity || entity instanceof FurnaceBoatEntity;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {

		Entity entity = context.getEntity();

		if (entity instanceof EnderChestBoatEntity enderChestBoatEntity) {
			if (context.player.shouldCancelInteraction() || ((BoatWithBlockEntityAccessor) enderChestBoatEntity).invokeCanAddPassenger(context.player)) {
				return Crosshair.INTERACTABLE;
			}
		}

		if (entity instanceof FurnaceBoatEntity furnaceBoatEntity) {
			if (FurnaceBoatEntityAccessor.getINGREDIENT().test(context.getItemStack()) && ((FurnaceBoatEntityAccessor) furnaceBoatEntity).getFuel() + 3600 <= 32000) {
				return Crosshair.USABLE;
			}
			if (!context.player.shouldCancelInteraction()) {
				return Crosshair.INTERACTABLE;
			}
		}

		return null;
	}
}
