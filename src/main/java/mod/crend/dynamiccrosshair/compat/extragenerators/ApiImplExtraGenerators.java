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
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return (block instanceof ColorfulGeneratorBlock
				|| block instanceof InfiniteGeneratorBlock
				|| block instanceof ItemGeneratorBlock
		);
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return (block instanceof FluidGeneratorBlock
				|| block instanceof FluidItemGeneratorBlock);
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		if (block instanceof FluidGeneratorBlock && context.getBlockEntity() instanceof FluidGeneratorBlockEntity blockEntity) {
			if (context.canInteractWithFluidStorage(blockEntity.getFluidInv())) {
				return Crosshair.USABLE;
			}
			return Crosshair.INTERACTABLE;
		}
		if (block instanceof FluidItemGeneratorBlock && context.getBlockEntity() instanceof FluidItemGeneratorBlockEntity blockEntity) {
			if (context.canInteractWithFluidStorage(blockEntity.getFluidInv())) {
				return Crosshair.USABLE;
			}
			return Crosshair.INTERACTABLE;
		}
		return null;
	}
}
