package mod.crend.dynamiccrosshair.compat.impl;

//? if wolves-with-armor {
import draylar.wolveswitharmor.WolvesWithArmor;
import draylar.wolveswitharmor.impl.WolfDataAccessor;
import draylar.wolveswitharmor.item.WolfArmorItem;
//?}
import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.WolfEntity;

public class ApiImplWolvesWithArmor implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "wolveswitharmor";
	}

	//? if wolves-with-armor {
	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();
		if (entity instanceof WolfEntity wolf) {
			if (wolf.getOwnerUuid() != null && wolf.getOwnerUuid().equals(context.getPlayer().getUuid())) {
				WolfDataAccessor data = WolvesWithArmor.getData(wolf);
				if (data.getWolfArmor().isEmpty() && context.getPlayer().getMainHandStack().getItem() instanceof WolfArmorItem) {
					return new Crosshair(InteractionType.PLACE_ITEM_ON_ENTITY);
				}

				if (!data.getWolfArmor().isEmpty() && context.getPlayer().getMainHandStack().isEmpty() && context.getPlayer().isSneaking()) {
					return new Crosshair(InteractionType.TAKE_ITEM_FROM_ENTITY);
				}
			}
		}

		return null;
	}
	//?}
}
