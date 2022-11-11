package mod.crend.dynamiccrosshair.compat.waystones;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import wraith.fwaystones.block.WaystoneBlock;
import wraith.fwaystones.item.*;

public class ApiImplWaystones implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "fwaystones";
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (item instanceof AbyssWatcherItem
				|| item instanceof PocketWormholeItem
				|| item instanceof ScrollOfInfiniteKnowledgeItem
		);
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (item instanceof LocalVoidItem
				|| item instanceof WaystoneScrollItem
				|| item instanceof WaystoneDebuggerItem);
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		if (context.getItem() instanceof WaystoneDebuggerItem && context.getEntity() instanceof ServerPlayerEntity) {
			return Crosshair.USABLE;
		}
		return null;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (!context.includeUsableItem()) return null;

		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof LocalVoidItem) {
			if (itemStack.hasNbt() && itemStack.getNbt().contains("fwaystones")) {
				if (context.player.isSneaking()) {
					return Crosshair.USABLE;
				} else if (!(item instanceof VoidTotem)) {
					return Crosshair.USABLE;
				}
			}
		}
		if (item instanceof WaystoneScrollItem) {
			if (itemStack.hasNbt() && itemStack.getNbt().contains("fwaystones")) {
				return Crosshair.USABLE;
			}
		}
		if (item instanceof WaystoneDebuggerItem && context.isWithBlock() && context.getBlock() instanceof WaystoneBlock) {
			return Crosshair.USABLE;
		}

		return null;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof WaystoneBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Item item = context.getItem();

		if (blockState.getBlock() instanceof WaystoneBlock) {
			if (item == Items.VINE || item == Items.SHEARS) {
				return Crosshair.USABLE;
			}
			if (!(item instanceof WaystoneScrollItem) && !(item instanceof LocalVoidItem) && !(item instanceof WaystoneDebuggerItem)) {
				return Crosshair.INTERACTABLE;
			}
		}
		return null;
	}
}
