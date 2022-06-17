package mod.crend.dynamiccrosshair.compat.botania;

import mod.crend.dynamiccrosshair.api.*;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;

public class ApiImplBotania implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return BotaniaAPI.MODID;
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		return BotaniaBlockInteractHandler.checkBlockInteractable(context);
	}

	@Override
	public Crosshair checkThrowable(CrosshairContext context) {
		return BotaniaItemHandler.checkThrowable(context);
	}

	@Override
	public Crosshair checkRangedWeapon(CrosshairContext context) {
		return BotaniaItemHandler.checkRangedWeapon(context);
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return BotaniaUsableItemHandler.isAlwaysUsableItem(itemStack);
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return BotaniaUsableItemHandler.isUsableItem(itemStack);
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		return BotaniaUsableItemHandler.checkUsableItem(context);
	}

	@Override
	public Crosshair checkEntity(CrosshairContext context) {
		return BotaniaEntityHandler.checkEntity(context);
	}
}
