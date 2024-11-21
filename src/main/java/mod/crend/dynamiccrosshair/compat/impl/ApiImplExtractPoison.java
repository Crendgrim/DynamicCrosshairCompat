package mod.crend.dynamiccrosshair.compat.impl;

import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.PufferfishEntity;
import net.minecraft.item.Items;

public class ApiImplExtractPoison implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "extractpoison-fabric";
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		if (context.getItem() == Items.GLASS_BOTTLE) {
			Entity entity = context.getEntity();
			if (entity instanceof CaveSpiderEntity || entity instanceof PufferfishEntity || entity instanceof BeeEntity) {
				return new Crosshair(InteractionType.USE_ITEM_ON_ENTITY);
			}
		}
		return null;
	}
}
