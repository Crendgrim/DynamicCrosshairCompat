package mod.crend.dynamiccrosshair.compat.impl;

//? if interactic {
import interactic.InteracticClientInit;
import interactic.InteracticInit;
import interactic.util.Helpers;
//?}
import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;

public class ApiImplInteractic implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "interactic";
	}

	//? if interactic {
	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (InteracticInit.getConfig().rightClickPickup()
				&& KeyBindingHelper.getBoundKeyOf(InteracticClientInit.PICKUP_ITEM) == InputUtil.UNKNOWN_KEY
				&& Helpers.raycastItem(MinecraftClient.getInstance().cameraEntity, MinecraftClient.getInstance().interactionManager.getReachDistance()) != null
		) {
			return new Crosshair(InteractionType.PICK_UP_ITEM);
		}

		return null;
	}
	//?}
}
