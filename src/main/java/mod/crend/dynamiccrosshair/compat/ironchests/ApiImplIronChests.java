package mod.crend.dynamiccrosshair.compat.ironchests;

import io.github.cyberanner.ironchests.IronChests;
import io.github.cyberanner.ironchests.blocks.ChestTypes;
import io.github.cyberanner.ironchests.blocks.GenericChestBlock;
import io.github.cyberanner.ironchests.items.UpgradeItem;
import io.github.cyberanner.ironchests.items.UpgradeTypes;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.mixin.ironchests.IUpgradeItemMixin;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ApiImplIronChests implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return IronChests.MOD_ID;
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
		return (item instanceof UpgradeItem);
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		if (context.isWithBlock()) {
			Block block = context.getBlock();

			if (context.getItem() instanceof UpgradeItem upgrade) {
				UpgradeTypes type = ((IUpgradeItemMixin) upgrade).getType();
				if (type.canUpgrade(ChestTypes.WOOD)) {
					if (block instanceof ChestBlock) {
						return Crosshair.USE_ITEM;
					}
				} else if (block.getDefaultState() == ChestTypes.get(type.source).getDefaultState()
						&& context.getBlockEntity() instanceof ChestBlockEntity chest
						&& ChestBlockEntity.getPlayersLookingInChestCount(context.world, context.getBlockPos()) == 0
						&& chest.canPlayerUse(context.player)
				) {
					return Crosshair.USE_ITEM;
				}
			}
		}

		return null;
	}
}
