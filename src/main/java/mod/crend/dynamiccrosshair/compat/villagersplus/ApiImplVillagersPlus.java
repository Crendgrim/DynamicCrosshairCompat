package mod.crend.dynamiccrosshair.compat.villagersplus;

import com.finallion.villagersplus.VillagersPlus;
import com.finallion.villagersplus.blocks.AlchemistTableBlock;
import com.finallion.villagersplus.blocks.HorticulturistTableBlock;
import com.finallion.villagersplus.blocks.OccultistTableBlock;
import com.finallion.villagersplus.blocks.OceanographerTableBlock;
import com.finallion.villagersplus.init.ModTags;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.ItemStack;

public class ApiImplVillagersPlus implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return VillagersPlus.MOD_ID;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof AlchemistTableBlock || block instanceof OccultistTableBlock;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof HorticulturistTableBlock || block instanceof OceanographerTableBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack itemStack = context.getItemStack();
		if (block instanceof HorticulturistTableBlock) {
			if (itemStack.isIn(ModTags.TALL_PLANTABLE_BLOCKS) && blockState.get(HorticulturistTableBlock.FLOWERS) == 0) {
				return Crosshair.USABLE;
			}
			if (itemStack.isIn(ModTags.SMALL_PLANTABLE_BLOCKS) && blockState.get(HorticulturistTableBlock.FLOWERS) < 4) {
				return Crosshair.USABLE;
			}
		}
		if (block instanceof OceanographerTableBlock) {
			if (itemStack.isIn(ModTags.AQUARIUM_PLANTABLE_BLOCKS) && blockState.get(OceanographerTableBlock.CORALS) < 4) {
				return Crosshair.USABLE;
			}

			if (itemStack.getItem() instanceof EntityBucketItem) {
				if (blockState.get(OceanographerTableBlock.FISH) < 1) {
					return Crosshair.USABLE;
				}
			}
		}

		return null;
	}
}
