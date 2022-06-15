package mod.crend.dynamiccrosshair.compat.macaw;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IBlockInteractHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.kikoz.mcwlights.MacawsLights;
import net.kikoz.mcwlights.objects.LightBaseShort;
import net.kikoz.mcwlights.objects.LightBaseTall;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class ApiImplMacawsLights implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return MacawsLights.MOD_ID;
	}

	@Override
	public IBlockInteractHandler getBlockInteractHandler() {
		return context -> {
			BlockState blockState = context.getBlockState();
			Block block = blockState.getBlock();
			if (block instanceof LightBaseShort
					|| (block instanceof LightBaseTall && (blockState.get(LightBaseTall.PART) == LightBaseTall.LightPart.BASE || (blockState.get(LightBaseTall.PART) == LightBaseTall.LightPart.TOP)))) {
				if (block.asItem() != context.getItem()) {
					return Crosshair.INTERACTABLE;
				}
			}
			return null;
		};
	}
}
