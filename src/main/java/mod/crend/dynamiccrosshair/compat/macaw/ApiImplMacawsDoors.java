package mod.crend.dynamiccrosshair.compat.macaw;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.kikoz.mcwdoors.MacawsDoors;
import net.kikoz.mcwdoors.objects.GarageDoor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class ApiImplMacawsDoors implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return MacawsDoors.MOD_ID;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof GarageDoor;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		Block block = context.getBlock();
		if (block instanceof GarageDoor && block.asItem() != context.getItem()) {
			return Crosshair.INTERACTABLE;
		}

		return null;
	}
}
