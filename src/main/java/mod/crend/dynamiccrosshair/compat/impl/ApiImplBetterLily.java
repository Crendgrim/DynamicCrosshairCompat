package mod.crend.dynamiccrosshair.compat.impl;

import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.block.Blocks;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PlaceableOnWaterItem;

public class ApiImplBetterLily implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "betterlily";
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		if (context.getBlock() instanceof LilyPadBlock) {
			ItemStack itemStack = context.getItemStack();
			if (!itemStack.isEmpty()
					&& !(itemStack.getItem() instanceof PlaceableOnWaterItem)
					&& context.getWorld().getBlockState(context.getBlockPos().down()).isOf(Blocks.WATER)
			) {
				return new Crosshair(InteractionType.PLACE_BLOCK);
			}
		}

		return null;
	}
}
