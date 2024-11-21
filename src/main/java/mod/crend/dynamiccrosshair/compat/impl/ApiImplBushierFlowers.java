package mod.crend.dynamiccrosshair.compat.impl;

import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ShearsItem;

//? if bushier-flowers {
import net.Pandarix.bushierflowers.block.custom.GrowableFlower;
import net.Pandarix.bushierflowers.block.custom.GrowableWitherRose;
//?}

public class ApiImplBushierFlowers implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "bushierflowers";
	}

	//? if bushier-flowers {
	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		Block block = context.getBlock();
		if (block instanceof FlowerBlock && context.getItem() instanceof BoneMealItem) {
			return new Crosshair(InteractionType.USE_ITEM_ON_BLOCK);
		}
		if ((block instanceof GrowableFlower || block instanceof GrowableWitherRose) && context.getItem() instanceof ShearsItem) {
			return new Crosshair(InteractionType.CORRECT_TOOL);
		}

		return null;
	}
	//?}
}
