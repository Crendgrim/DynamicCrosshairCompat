package mod.crend.dynamiccrosshair.compat.wolveswitharmor;

import draylar.wolveswitharmor.WolvesWithArmor;
import draylar.wolveswitharmor.impl.WolfDataAccessor;
import draylar.wolveswitharmor.item.WolfArmorItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.WolfEntity;

public class ApiImplWolvesWithArmor implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "wolveswitharmor";
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();
		if (entity instanceof WolfEntity wolf) {
			if (wolf.getOwnerUuid() != null && wolf.getOwnerUuid().equals(context.player.getUuid())) {
				WolfDataAccessor data = WolvesWithArmor.getData(wolf);
				if (data.getWolfArmor().isEmpty() && context.player.getMainHandStack().getItem() instanceof WolfArmorItem) {
					return Crosshair.USABLE;
				}

				if (!data.getWolfArmor().isEmpty() && context.player.getMainHandStack().isEmpty() && context.player.isSneaking()) {
					return Crosshair.USABLE;
				}
			}
		}

		return null;
	}
}
