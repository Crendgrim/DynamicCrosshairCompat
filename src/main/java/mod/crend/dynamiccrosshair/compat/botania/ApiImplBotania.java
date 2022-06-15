package mod.crend.dynamiccrosshair.compat.botania;

import mod.crend.dynamiccrosshair.api.*;
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

	BotaniaItemHandler itemHandler = new BotaniaItemHandler();
	BotaniaEntityHandler entityHandler = new BotaniaEntityHandler();
	IBlockInteractHandler blockInteractHandler = new BotaniaBlockInteractHandler();
	BotaniaUsableItemHandler usableItemHandler = new BotaniaUsableItemHandler();

	@Override
	public IBlockInteractHandler getBlockInteractHandler() {
		return blockInteractHandler;
	}

	@Override
	public IThrowableItemHandler getThrowableItemHandler() {
		return itemHandler;
	}

	@Override
	public IRangedWeaponHandler getRangedWeaponHandler() {
		return itemHandler;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return usableItemHandler.isAlwaysUsableItem(itemStack);
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return usableItemHandler.isUsableItem(itemStack);
	}

	@Override
	public IUsableItemHandler getUsableItemHandler() {
		return usableItemHandler;
	}

	@Override
	public IEntityHandler getEntityHandler() {
		return entityHandler;
	}
}
