package mod.crend.dynamiccrosshair.compat.soulsweapons;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;

public class ApiImplSoulsWeaponry implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "soulsweapons";
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return SoulsWeaponryHandler.isInteractableBlock(blockState);
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		return SoulsWeaponryHandler.computeFromBlock(context);
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return SoulsWeaponryHandler.isAlwaysUsableItem(itemStack);
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return SoulsWeaponryHandler.isUsableItem(itemStack);
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		return SoulsWeaponryHandler.computeFromItem(context);
	}
}
