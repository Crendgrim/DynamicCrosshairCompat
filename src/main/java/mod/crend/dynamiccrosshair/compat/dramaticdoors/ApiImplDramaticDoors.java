package mod.crend.dynamiccrosshair.compat.dramaticdoors;

import com.fizzware.dramaticdoors.DDTags;
import com.fizzware.dramaticdoors.DramaticDoors;
import com.fizzware.dramaticdoors.blocks.DDBlocks;
import com.fizzware.dramaticdoors.blocks.TallDoorBlock;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.mixin.IAbstractBlockMixin;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;

public class ApiImplDramaticDoors implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return DramaticDoors.MOD_ID;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = context.getBlock();
		if (block instanceof TallDoorBlock door) {
			if (((IAbstractBlockMixin) block).getMaterial() != Material.METAL || blockState.isIn(DDTags.HAND_OPENABLE_TALL_METAL_DOORS)) {
				if (door != DDBlocks.TALL_GOLD_DOOR || !blockState.get(TallDoorBlock.POWERED)) {
					return Crosshair.INTERACTABLE;
				}
			}
		}

		return null;
	}
}
