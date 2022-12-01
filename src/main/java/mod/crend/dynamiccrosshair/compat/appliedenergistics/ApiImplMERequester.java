package mod.crend.dynamiccrosshair.compat.appliedenergistics;

import com.almostreliable.merequester.requester.RequesterBlock;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;

public class ApiImplMERequester implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "merequester";
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof RequesterBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		if (context.getBlock() instanceof RequesterBlock && !context.player.isSneaking()) {
			return Crosshair.INTERACTABLE;
		}
		return null;
	}
}
