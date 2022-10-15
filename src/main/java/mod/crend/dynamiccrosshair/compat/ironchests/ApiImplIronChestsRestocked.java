package mod.crend.dynamiccrosshair.compat.ironchests;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tech.thatgravyboat.ironchests.IronChests;
import tech.thatgravyboat.ironchests.common.blocks.GenericChestBlock;
import tech.thatgravyboat.ironchests.common.blocks.GenericChestBlockEntity;
import tech.thatgravyboat.ironchests.common.blocks.LockState;
import tech.thatgravyboat.ironchests.common.items.DollyItem;
import tech.thatgravyboat.ironchests.common.items.KeyItem;
import tech.thatgravyboat.ironchests.common.items.LockItem;
import tech.thatgravyboat.ironchests.common.items.UpgradeItem;

public class ApiImplIronChestsRestocked implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return IronChests.MODID;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		Block block = context.getBlock();

		if (block instanceof GenericChestBlock) {
			return Crosshair.INTERACTABLE;
		}

		return null;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (item instanceof DollyItem || item instanceof KeyItem);
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		if (context.isWithBlock()) {

			ItemStack itemStack = context.getItemStack();
			Item item = itemStack.getItem();
			BlockState blockState = context.getBlockState();
			Block block = blockState.getBlock();

			if (item instanceof DollyItem) {
				if (DollyItem.hasChest(itemStack) && context.player.isSneaking()) {
					return Crosshair.HOLDING_BLOCK;
				}
				if (!blockState.isIn(DollyItem.NONPICKABLE_CHEST_TAG) && !DollyItem.hasChest(itemStack)) {
					return Crosshair.USE_ITEM;
				}
			}
			if (item instanceof KeyItem) {
				if (block instanceof GenericChestBlock && context.getBlockEntity() instanceof GenericChestBlockEntity chestBlockEntity) {
					if (chestBlockEntity.viewers() == 0 && !blockState.get(GenericChestBlock.LOCK).equals(LockState.NO_LOCK)) {
						if (itemStack.hasNbt() && itemStack.getNbt().contains("key")) {
							if (chestBlockEntity.isRightKey(itemStack)) {
								return Crosshair.USE_ITEM;
							}
						} else {
							if (blockState.get(GenericChestBlock.LOCK).equals(LockState.UNLOCKED)){
								return Crosshair.USE_ITEM;
							}
						}
					}
				}
			}
			if (item instanceof LockItem) {
				if (block instanceof GenericChestBlock && context.getBlockEntity() instanceof GenericChestBlockEntity chestBlockEntity) {
					if (chestBlockEntity.viewers() == 0 && blockState.get(GenericChestBlock.LOCK).equals(LockState.NO_LOCK)) {
						return Crosshair.USE_ITEM;
					}
				}
			}
			if (item instanceof UpgradeItem upgrade) {
				BlockEntity blockEntity = context.getBlockEntity();
				if (blockEntity instanceof GenericChestBlockEntity chestBlockEntity) {
					if (chestBlockEntity.viewers() == 0
							&& chestBlockEntity.getChestType().equals(upgrade.type.from())
							&& chestBlockEntity.checkUnlocked(context.player)
					) {
						return Crosshair.USE_ITEM;
					}
				} else if (blockEntity instanceof ChestBlockEntity chestBlockEntity){
					if (ChestBlockEntity.getPlayersLookingInChestCount(context.world, context.getBlockPos()) == 0
							&& blockState.isIn(UpgradeItem.REPLACEABLE_CHEST_TAG)
							&& upgrade.type.from() == null
							&& chestBlockEntity.checkUnlocked(context.player)
					) {
						return Crosshair.USE_ITEM;
					}
				}
			}

		}


		return null;
	}
}
