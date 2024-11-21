package mod.crend.dynamiccrosshair.compat.impl;

import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
//? if bewitchment
import moriyashiine.bewitchment.common.block.entity.LockableBlockEntity;

public class ApiImplBewitchment implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "bewitchment";
	}

	//? if bewitchment {
	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		if (context.getBlockEntity() instanceof LockableBlockEntity lockable) {
			if (!lockable.test(context.getPlayer())) {
				return new Crosshair(InteractionType.NO_ACTION);
			}
		}
		return null;
	}
	//?}
}
