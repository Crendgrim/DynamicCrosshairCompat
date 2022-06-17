package mod.crend.dynamiccrosshair.compat.kelpfertilizer;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
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
	public boolean isUsableItem(ItemStack itemStack) {
		return itemStack.isOf(Items.KELP);
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		if (context.isWithBlock()) {
			BlockState blockState = context.getBlockState();

			if (blockState.getBlock() instanceof Fertilizable fertilizable && fertilizable.isFertilizable(context.world, context.getBlockPos(), blockState, true)) {
				return Crosshair.USE_ITEM;
			}
		}
		return null;
	}
}
