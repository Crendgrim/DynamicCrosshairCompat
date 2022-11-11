package mod.crend.dynamiccrosshair.compat.clickmachine;

import com.kenza.clickmachine.blocks.AutoClickerBlock;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import uk.co.cablepost.autoworkstations.auto_crafting_table.AutoCraftingTableBlock;

public class ApiImplClickMachine implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "clickmachine";
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof AutoClickerBlock;
	}
}
