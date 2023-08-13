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
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof GenericChestBlock;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (item instanceof DollyItem || item instanceof KeyItem || item instanceof UpgradeItem);
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (context.isWithBlock() && context.includeUsableItem()) {

			ItemStack itemStack = context.getItemStack();
			Item item = itemStack.getItem();
			BlockState blockState = context.getBlockState();
			Block block = blockState.getBlock();

			if (item instanceof DollyItem) {
				if (DollyItem.hasChest(itemStack) && context.player.isSneaking()) {
					return Crosshair.HOLDING_BLOCK;
				}
				if (!blockState.isIn(DollyItem.NONPICKABLE_CHEST_TAG) && !DollyItem.hasChest(itemStack)) {
					return Crosshair.USABLE;
				}
			}
			if (item instanceof KeyItem) {
				if (block instanceof GenericChestBlock && context.getBlockEntity() instanceof GenericChestBlockEntity chestBlockEntity) {
					if (chestBlockEntity.viewers() == 0 && !blockState.get(GenericChestBlock.LOCK).equals(LockState.NO_LOCK)) {
						if (itemStack.hasNbt() && itemStack.getNbt().contains("key")) {
							if (chestBlockEntity.isRightKey(itemStack.getNbt().getUuid("key"))) {
								return Crosshair.USABLE;
							}
						} else {
							if (blockState.get(GenericChestBlock.LOCK).equals(LockState.UNLOCKED)){
								return Crosshair.USABLE;
							}
						}
					}
				}
			}
			if (item instanceof LockItem) {
				if (block instanceof GenericChestBlock && context.getBlockEntity() instanceof GenericChestBlockEntity chestBlockEntity) {
					if (chestBlockEntity.viewers() == 0 && blockState.get(GenericChestBlock.LOCK).equals(LockState.NO_LOCK)) {
						return Crosshair.USABLE;
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
						return Crosshair.USABLE;
					}
				} else if (blockEntity instanceof ChestBlockEntity chestBlockEntity){
					if (ChestBlockEntity.getPlayersLookingInChestCount(context.world, context.getBlockPos()) == 0
							&& blockState.isIn(UpgradeItem.REPLACEABLE_CHEST_TAG)
							&& upgrade.type.from() == null
							&& chestBlockEntity.checkUnlocked(context.player)
					) {
						return Crosshair.USABLE;
					}
				}
			}

		}


		return null;
	}
}
