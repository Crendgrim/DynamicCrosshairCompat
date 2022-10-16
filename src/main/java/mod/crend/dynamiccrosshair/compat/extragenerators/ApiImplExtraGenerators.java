package mod.crend.dynamiccrosshair.compat.extragenerators;

import io.github.lucaargolo.extragenerators.ExtraGenerators;
import io.github.lucaargolo.extragenerators.common.block.*;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class ApiImplExtraGenerators implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return ExtraGenerators.MOD_ID;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		if (block instanceof ColorfulGeneratorBlock
				|| block instanceof FluidGeneratorBlock // TODO fluid handling
				|| block instanceof FluidItemGeneratorBlock // TODO fluid handling
				|| block instanceof InfiniteGeneratorBlock
				|| block instanceof ItemGeneratorBlock
		) {
			return Crosshair.INTERACTABLE;
		}
		return null;
	}
}
