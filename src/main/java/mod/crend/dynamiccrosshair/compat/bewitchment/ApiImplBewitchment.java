package mod.crend.dynamiccrosshair.compat.bewitchment;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import moriyashiine.bewitchment.common.Bewitchment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class ApiImplBewitchment implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Bewitchment.MOD_ID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return BewitchmentHandler.isAlwaysUsableItem(itemStack);
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		return BewitchmentHandler.computeFromItem(context);
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return BewitchmentHandler.isAlwaysInteractableBlock(blockState);
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return BewitchmentHandler.isInteractableBlock(blockState);
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		return BewitchmentHandler.computeFromBlock(context);
	}

	@Override
	public boolean isInteractableEntity(Entity entity) {
		return BewitchmentHandler.isInteractableEntity(entity);
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		return BewitchmentHandler.computeFromEntity(context);
	}
}
