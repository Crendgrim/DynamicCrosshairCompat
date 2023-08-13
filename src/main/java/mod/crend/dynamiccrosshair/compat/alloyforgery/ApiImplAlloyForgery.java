package mod.crend.dynamiccrosshair.compat.alloyforgery;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import wraith.alloyforgery.AlloyForgery;
import wraith.alloyforgery.block.ForgeControllerBlock;
import wraith.alloyforgery.block.ForgeControllerBlockEntity;
import wraith.alloyforgery.forges.ForgeFuelRegistry;

public class ApiImplAlloyForgery implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return AlloyForgery.MOD_ID;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof ForgeControllerBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		ItemStack itemStack = context.getItemStack();
		if (blockState.getBlock() instanceof ForgeControllerBlock && context.getBlockEntity() instanceof ForgeControllerBlockEntity controller) {
			ForgeFuelRegistry.ForgeFuelDefinition fuelDefinition = ForgeFuelRegistry.getFuelForItem(itemStack.getItem());

			if (fuelDefinition.hasReturnType() && controller.canAddFuel(fuelDefinition.fuel())) {
				return Crosshair.USABLE;
			} else {
				if (controller.verifyMultiblock()) {
					return Crosshair.INTERACTABLE;
				}
			}
		}
		return null;
	}
}
