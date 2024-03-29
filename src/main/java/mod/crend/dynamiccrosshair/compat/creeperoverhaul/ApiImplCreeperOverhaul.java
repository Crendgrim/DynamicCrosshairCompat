package mod.crend.dynamiccrosshair.compat.creeperoverhaul;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

public class ApiImplCreeperOverhaul implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Creepers.MODID;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();

		if (entity instanceof BaseCreeper creeper) {
			Item item = context.getItem();
			if (creeper.type.melee() == 0 && item instanceof FlintAndSteelItem) {
				return Crosshair.USABLE;
			}
			if (creeper.type.isShearable() && !creeper.isSheared() && item instanceof ShearsItem) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
