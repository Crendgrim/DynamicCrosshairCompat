package mod.crend.dynamiccrosshair.compat.interactic;

import interactic.InteracticClientInit;
import interactic.InteracticInit;
import interactic.util.Helpers;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;

public class ApiImplInteractic implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return InteracticInit.MOD_ID;
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (!InteracticInit.getConfig().rightClickPickup) return null;
		if (KeyBindingHelper.getBoundKeyOf(InteracticClientInit.PICKUP_ITEM) != InputUtil.UNKNOWN_KEY) return null;

		if (Helpers.raycastItem(MinecraftClient.getInstance().cameraEntity, MinecraftClient.getInstance().interactionManager.getReachDistance()) == null)
			return null;

		return Crosshair.INTERACTABLE;
	}
}
