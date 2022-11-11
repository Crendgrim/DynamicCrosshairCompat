package mod.crend.dynamiccrosshair.compat.extractpoison;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
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
				return Crosshair.USABLE;
			}
		}
		return null;
	}
}
