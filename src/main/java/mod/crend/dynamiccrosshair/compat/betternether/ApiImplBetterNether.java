package mod.crend.dynamiccrosshair.compat.betternether;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.bclib.BCLibUsableItemHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blocks.BNChair;

public class ApiImplBetterNether implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return BetterNether.MOD_ID;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		Block block = context.getBlock();
		if (block instanceof BNChair) {
			return Crosshair.INTERACTABLE;
		}
		return null;
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		return BCLibUsableItemHandler.checkUsableItem(context);
	}
}
