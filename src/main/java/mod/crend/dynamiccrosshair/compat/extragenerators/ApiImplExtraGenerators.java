package mod.crend.dynamiccrosshair.compat.extragenerators;

import io.github.lucaargolo.extragenerators.ExtraGenerators;
import io.github.lucaargolo.extragenerators.common.block.*;
import io.github.lucaargolo.extragenerators.common.blockentity.FluidGeneratorBlockEntity;
import io.github.lucaargolo.extragenerators.common.blockentity.FluidItemGeneratorBlockEntity;
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
				|| block instanceof InfiniteGeneratorBlock
				|| block instanceof ItemGeneratorBlock
		) {
			return Crosshair.INTERACTABLE;
		}
		if (block instanceof FluidGeneratorBlock && context.getBlockEntity() instanceof FluidGeneratorBlockEntity blockEntity) {
			if (context.canInteractWithFluidStorage(blockEntity.getFluidInv())) {
				return Crosshair.USE_ITEM;
			}
			return Crosshair.INTERACTABLE;
		}
		if (block instanceof FluidItemGeneratorBlock && context.getBlockEntity() instanceof FluidItemGeneratorBlockEntity blockEntity) {
			if (context.canInteractWithFluidStorage(blockEntity.getFluidInv())) {
				return Crosshair.USE_ITEM;
			}
			return Crosshair.INTERACTABLE;
		}
		return null;
	}
}
