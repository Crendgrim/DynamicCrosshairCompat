package mod.crend.dynamiccrosshair.compat.halfdoors;

import amymialee.halfdoors.Halfdoors;
import amymialee.halfdoors.blocks.HalfDoorBlock;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.mixin.IAbstractBlockMixin;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;

public class ApiImplHalfDoors implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Halfdoors.MOD_ID;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return (block instanceof HalfDoorBlock && ((IAbstractBlockMixin) block).getMaterial() != Material.METAL);
	}
}
