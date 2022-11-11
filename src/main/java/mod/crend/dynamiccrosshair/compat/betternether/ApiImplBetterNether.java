package mod.crend.dynamiccrosshair.compat.betternether;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.bclib.BCLibUsableItemHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.betterx.betternether.BetterNether;
import org.betterx.betternether.blocks.BNChair;

public class ApiImplBetterNether implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return BetterNether.MOD_ID;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof BNChair;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (context.includeUsableItem()) {
			return BCLibUsableItemHandler.checkUsableItem(context);
		}
		return null;
	}
}
