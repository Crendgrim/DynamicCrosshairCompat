package mod.crend.dynamiccrosshair.compat.cyclepaintings;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
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
			return Crosshair.INTERACTABLE;
		}
		return null;
	}
}
