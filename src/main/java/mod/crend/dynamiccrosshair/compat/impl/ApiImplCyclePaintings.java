package mod.crend.dynamiccrosshair.compat.impl;

import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.item.Items;

public class ApiImplCyclePaintings implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "cyclepaintings-fabric";
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		if (context.getEntity() instanceof PaintingEntity && context.getItemStack().isOf(Items.PAINTING)) {
			return new Crosshair(InteractionType.INTERACT_WITH_ENTITY);
		}
		return null;
	}
}
