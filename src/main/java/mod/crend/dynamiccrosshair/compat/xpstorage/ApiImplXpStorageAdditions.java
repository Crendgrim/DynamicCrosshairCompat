package mod.crend.dynamiccrosshair.compat.xpstorage;

import com.notker.xps_additions.XpsAdditions;
import com.notker.xps_additions.blocks.XpItemInserter;
import com.notker.xps_additions.items.StaffOfRebark;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.mixin.xps_additions.StaffOfRebarkAccessor;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;

import java.util.Optional;

public class ApiImplXpStorageAdditions implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return XpsAdditions.MOD_ID;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof XpItemInserter;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (itemStack.getItem() instanceof StaffOfRebark staff && context.isWithBlock()) {
			Optional<BlockState> unStrippedState = ((StaffOfRebarkAccessor) staff).invokeGetUnStrippedState(context.getBlockState());
			if (unStrippedState.isPresent()) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
