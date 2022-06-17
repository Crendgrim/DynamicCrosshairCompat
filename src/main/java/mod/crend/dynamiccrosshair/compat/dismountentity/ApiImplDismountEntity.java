package mod.crend.dynamiccrosshair.compat.dismountentity;

import com.natamus.dismountentity.util.Reference;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.entity.Entity;

import java.util.List;

public class ApiImplDismountEntity implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Reference.MOD_ID;
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair checkEntity(CrosshairContext context) {
		if (context.player.shouldCancelInteraction()) {
			List<Entity> mounted = context.getEntity().getPassengerList();
			if (mounted.size() > 0) {
				return Crosshair.INTERACTABLE;
			}
		}

		return null;
	}
}
