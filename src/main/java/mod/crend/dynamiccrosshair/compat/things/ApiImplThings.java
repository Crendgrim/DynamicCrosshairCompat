package mod.crend.dynamiccrosshair.compat.things;

import com.glisco.things.Things;
import com.glisco.things.blocks.ThingsBlocks;
import com.glisco.things.items.ThingsItems;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.item.ItemStack;

public class ApiImplThings implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Things.MOD_ID;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return blockState.isOf(ThingsBlocks.PLACED_ITEM);
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		if (context.getBlock() == ThingsBlocks.PLACED_ITEM) {
			if (!context.player.isSneaking()) {
				return Crosshair.INTERACTABLE;
			}
		}

		return null;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return itemStack.isOf(ThingsItems.THINGS_ALMANAC)
				|| itemStack.isOf(ThingsItems.DISPLACEMENT_TOME)
				|| itemStack.isOf(ThingsItems.ITEM_MAGNET);
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return itemStack.isOf(ThingsItems.CONTAINER_KEY);
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (context.isWithBlock() && context.includeUsableItem()) {
			ItemStack itemStack = context.getItemStack();

			if (itemStack.isOf(ThingsItems.CONTAINER_KEY)) {
				if (context.player.isSneaking() && context.getBlockEntity() instanceof LockableContainerBlockEntity) {
					return Crosshair.USABLE;
				}
			}
		}

		return null;
	}
}
