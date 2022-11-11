package mod.crend.dynamiccrosshair.compat.appliedenergistics;

import appeng.core.AppEng;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;

public class ApiImplAppliedEnergistics2 implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return AppEng.MOD_ID;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return AE2Handler.isAlwaysInteractableBlock(blockState);
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return AE2Handler.isInteractableBlock(blockState);
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return AE2Handler.isAlwaysUsableItem(itemStack);
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return AE2Handler.isUsableItem(itemStack);
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (context.includeUsableItem()) {
			return AE2Handler.checkUsableItem(context);
		}
		return null;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		return AE2Handler.computeFromBlock(context);
	}
}
