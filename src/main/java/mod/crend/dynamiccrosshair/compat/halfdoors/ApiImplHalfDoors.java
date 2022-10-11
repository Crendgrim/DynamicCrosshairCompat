package mod.crend.dynamiccrosshair.compat.halfdoors;

import amymialee.halfdoors.Halfdoors;
import amymialee.halfdoors.blocks.HalfDoorBlock;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
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
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();

		if (block instanceof HalfDoorBlock && ((IAbstractBlockMixin) block).getMaterial() != Material.METAL) {
			return Crosshair.INTERACTABLE;
		}

		return null;
	}
}
