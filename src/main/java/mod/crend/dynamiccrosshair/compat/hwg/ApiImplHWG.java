package mod.crend.dynamiccrosshair.compat.hwg;

import mod.azure.hwg.HWGMod;
import mod.azure.hwg.blocks.FuelTankBlock;
import mod.azure.hwg.blocks.GunTableBlock;
import mod.azure.hwg.item.ammo.*;
import mod.azure.hwg.item.weapons.HWGGunBase;
import mod.azure.hwg.item.weapons.HWGGunLoadedBase;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.ItemCategory;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ApiImplHWG implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return HWGMod.MODID;
	}

	@Override
	public ItemCategory getItemCategory(ItemStack itemStack) {
		Item item = itemStack.getItem();

		if (item instanceof GrenadeEmpItem
				|| item instanceof GrenadeFragItem
				|| item instanceof GrenadeNapalmItem
				|| item instanceof GrenadeSmokeItem
				|| item instanceof GrenadeStunItem
		) {
			return ItemCategory.THROWABLE;
		}

		if (item instanceof HWGGunBase || item instanceof HWGGunLoadedBase) {
			return ItemCategory.RANGED_WEAPON;
		}

		return DynamicCrosshairApi.super.getItemCategory(itemStack);
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof GrenadeEmpItem
				|| item instanceof GrenadeFragItem
				|| item instanceof GrenadeNapalmItem
				|| item instanceof GrenadeSmokeItem
				|| item instanceof GrenadeStunItem
		) {
			return Crosshair.THROWABLE;
		}

		if (item instanceof HWGGunBase) {
			return Crosshair.RANGED_WEAPON;
		}

		if (item instanceof HWGGunLoadedBase) {
			if (itemStack.getNbt() != null && itemStack.getNbt().getBoolean("Charged")) {
				if (itemStack.getDamage() < itemStack.getMaxDamage() - 1) {
					return Crosshair.RANGED_WEAPON;
				}
			} else if (!context.player.getProjectileType(itemStack).isEmpty()) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof GunTableBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		Block block = context.getBlock();
		Item item = context.getItem();

		if (block instanceof FuelTankBlock) {
			if (item == Items.FLINT_AND_STEEL || item == Items.FIRE_CHARGE) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
