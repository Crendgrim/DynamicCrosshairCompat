package mod.crend.dynamiccrosshair.compat.bountiful;

import io.ejekta.bountiful.Bountiful;
import io.ejekta.bountiful.content.BountyItem;
import io.ejekta.bountiful.content.board.BoardBlock;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;

public class ApiImplBountiful implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Bountiful.ID;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof BoardBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		if (context.getBlock() instanceof BoardBlock && !context.player.isSneaking()) {
			if (context.getItem() instanceof BountyItem) {
				return Crosshair.USABLE;
			} else {
				return Crosshair.INTERACTABLE;
			}
		}
		return null;
	}
}
