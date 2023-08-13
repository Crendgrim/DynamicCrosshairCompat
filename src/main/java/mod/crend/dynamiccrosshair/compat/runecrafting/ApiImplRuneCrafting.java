package mod.crend.dynamiccrosshair.compat.runecrafting;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.runes.RunesMod;
import net.runes.crafting.RuneCraftingBlock;

public class ApiImplRuneCrafting implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return RunesMod.ID;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof RuneCraftingBlock;
	}
}
