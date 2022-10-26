package mod.crend.dynamiccrosshair.compat.storagemod;

import com.tom.storagemod.StorageMod;
import com.tom.storagemod.StorageTags;
import com.tom.storagemod.block.*;
import com.tom.storagemod.item.AdvWirelessTerminalItem;
import com.tom.storagemod.item.PaintKitItem;
import com.tom.storagemod.item.WirelessTerminal;
import com.tom.storagemod.item.WirelessTerminalItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;

public class ApiImplStorageMod implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return StorageMod.modid;
	}

	@Override
	public boolean forceInvalidate(CrosshairContext context) {
		return !context.isTargeting() && context.getItem() instanceof WirelessTerminalItem;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();

		if (block instanceof AbstractStorageTerminalBlock) {
			return Crosshair.INTERACTABLE;
		}
		if (block instanceof BasicInventoryHopperBlock) {
			if (context.getItemStack().isEmpty()) {
				return Crosshair.INTERACTABLE;
			}
			return Crosshair.USE_ITEM;
		}
		if (block instanceof FilteredInventoryCableConnectorBlock) {
			return Crosshair.INTERACTABLE;
		}
		if (block instanceof InventoryCableConnectorBlock) {
			Direction f = blockState.get(InventoryCableConnectorBlock.FACING);
			BlockState pointedAt = context.world.getBlockState(context.getBlockPos().offset(f));
			if (context.getItemStack().isEmpty() && pointedAt.isOf(Blocks.BEACON)) {
				return Crosshair.INTERACTABLE;
			}
		}
		if (block instanceof InventoryConnectorBlock) {
			return Crosshair.INTERACTABLE;
		}
		if (block instanceof InventoryProxyBlock) {
			if (context.getItemStack().isOf(Items.DIAMOND) && blockState.get(InventoryProxyBlock.FACING) != context.getBlockHitSide()) {
				if (blockState.get(InventoryProxyBlock.FILTER_FACING) == InventoryProxyBlock.DirectionWithNull.NULL) {
					return Crosshair.USE_ITEM;
				}
			}
		}
		if (block instanceof LevelEmitterBlock) {
			return Crosshair.INTERACTABLE;
		}

		return null;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof PaintKitItem || item instanceof WirelessTerminal;
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof PaintKitItem && context.isWithBlock()) {
			if (context.player.shouldCancelInteraction() || context.getBlock() instanceof IPaintable) {
				return Crosshair.USE_ITEM;
			}
		}
		if (item instanceof WirelessTerminalItem) {
			BlockHitResult lookingAt = (BlockHitResult)context.player.raycast(StorageMod.CONFIG.wirelessRange, 0.0F, true);
			BlockState state = context.world.getBlockState(lookingAt.getBlockPos());
			if (state.isIn(StorageTags.REMOTE_ACTIVATE)) {
				return Crosshair.USE_ITEM;
			}
		}
		if (item instanceof AdvWirelessTerminalItem) {
			if (context.isWithBlock() && context.player.shouldCancelInteraction()) {
				if (context.getBlockState().isIn(StorageTags.REMOTE_ACTIVATE)) {
					return Crosshair.USE_ITEM;
				}
			} else if (itemStack.hasNbt() && itemStack.getNbt().contains("BindX")) {
				return Crosshair.USE_ITEM;
			}
		}

		return null;
	}
}
