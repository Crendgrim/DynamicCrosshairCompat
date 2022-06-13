package mod.crend.dynamiccrosshair.compat.macaw;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IBlockInteractHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.kikoz.mcwlights.MacawsLights;
import net.kikoz.mcwlights.objects.LightBaseShort;
import net.kikoz.mcwlights.objects.LightBaseTall;
import net.minecraft.block.Block;

public class ApiImplMacawsLights implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return MacawsLights.MOD_ID;
	}

	@Override
	public IBlockInteractHandler getBlockInteractHandler() {
		return (player, itemStack, blockPos, blockState) -> {
			Block block = blockState.getBlock();
			if (block instanceof LightBaseShort
					|| (block instanceof LightBaseTall && (blockState.get(LightBaseTall.PART) == LightBaseTall.LightPart.BASE || (blockState.get(LightBaseTall.PART) == LightBaseTall.LightPart.TOP)))) {
				if (block.asItem() != itemStack.getItem()) {
					return Crosshair.INTERACTABLE;
				}
			}
			return null;
		};
	}
}
