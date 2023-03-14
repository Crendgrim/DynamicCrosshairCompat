package mod.crend.dynamiccrosshair.compat.create;

import com.simibubi.create.Create;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.ItemCategory;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;

public class ApiImplCreate implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Create.ID;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return CreateBlockHandler.isAlwaysInteractableBlock(blockState);
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return CreateBlockHandler.isInteractableBlock(blockState);
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		return CreateBlockHandler.computeFromBlock(context);
	}

	@Override
	public ItemCategory getItemCategory(ItemStack itemStack) {
		ItemCategory category = CreateItemHandler.getItemCategory(itemStack);
		if (category != ItemCategory.NONE) return category;

		return DynamicCrosshairApi.super.getItemCategory(itemStack);
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return CreateItemHandler.isAlwaysUsableItem(itemStack);
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return CreateItemHandler.isUsableItem(itemStack);
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		return CreateItemHandler.computeFromItem(context);
	}
}
