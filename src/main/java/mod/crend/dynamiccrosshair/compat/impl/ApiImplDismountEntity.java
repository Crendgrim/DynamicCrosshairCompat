package mod.crend.dynamiccrosshair.compat.impl;

import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.entity.Entity;
import java.util.List;

public class ApiImplDismountEntity implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "dismountentity";
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		if (context.getPlayer().shouldCancelInteraction()) {
			List<Entity> mounted = context.getEntity().getPassengerList();
			if (!mounted.isEmpty()) {
				return new Crosshair(InteractionType.INTERACT_WITH_ENTITY);
			}
		}

		return null;
	}
}
