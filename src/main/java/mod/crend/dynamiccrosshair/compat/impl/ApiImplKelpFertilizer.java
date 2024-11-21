package mod.crend.dynamiccrosshair.compat.impl;

import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ApiImplKelpFertilizer implements DynamicCrosshairApi {

	@Override
	public String getNamespace() {
		return "kelpfertilizer-fabric";
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public boolean isUsable(ItemStack itemStack) {
		return itemStack.isOf(Items.KELP);
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (context.isWithBlock() && context.includeUsableItem()) {
			BlockState blockState = context.getBlockState();

			if (context.getItemStack().isOf(Items.KELP)
					&& blockState.getBlock() instanceof Fertilizable fertilizable
					&& fertilizable.isFertilizable(context.getWorld(), context.getBlockPos(), blockState/*? if <1.20.4 {*/, true/*?}*/)
			) {
				return new Crosshair(InteractionType.USE_ITEM_ON_BLOCK);
			}
		}
		return null;
	}
}
