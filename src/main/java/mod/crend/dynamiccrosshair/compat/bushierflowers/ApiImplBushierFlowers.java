package mod.crend.dynamiccrosshair.compat.bushierflowers;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.Pandarix.bushierflowers.BushierFlowers;
import net.Pandarix.bushierflowers.block.custom.GrowableFlower;
import net.Pandarix.bushierflowers.block.custom.GrowableWitherRose;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ShearsItem;

public class ApiImplBushierFlowers implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return BushierFlowers.MOD_ID;
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		Block block = context.getBlock();
		if (block instanceof FlowerBlock && context.getItem() instanceof BoneMealItem) {
			return Crosshair.USE_ITEM;
		}
		if ((block instanceof GrowableFlower || block instanceof GrowableWitherRose) && context.getItem() instanceof ShearsItem) {
			return Crosshair.CORRECT_TOOL;
		}

		return null;
	}
}
