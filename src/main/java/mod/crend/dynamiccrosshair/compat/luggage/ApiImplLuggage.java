package mod.crend.dynamiccrosshair.compat.luggage;

import com.gizmo.luggage.Luggage;
import com.gizmo.luggage.entity.LuggageEntity;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.Items;

public class ApiImplLuggage implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Luggage.ID;
	}

	@Override
	public Crosshair checkEntity(CrosshairContext context) {
		if (context.getEntity() instanceof LuggageEntity) {
			if (context.getItemStack().isOf(Items.NAME_TAG)) {
				return Crosshair.USE_ITEM;
			}
			return Crosshair.INTERACTABLE;
		}
		return null;
	}
}
