//? if ironchestsrestocked {
package mod.crend.dynamiccrosshair.compat.mixin.ironchests;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import tech.thatgravyboat.ironchests.common.blocks.GenericChestBlock;
import tech.thatgravyboat.ironchests.common.blocks.GenericChestBlockEntity;
import tech.thatgravyboat.ironchests.common.blocks.LockState;
import tech.thatgravyboat.ironchests.common.items.KeyItem;
import tech.thatgravyboat.ironchests.common.items.LockItem;

@Mixin(value = GenericChestBlock.class, remap = false)
public class GenericChestBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof GenericChestBlockEntity chestBlockEntity) {
			BlockState blockState = context.getBlockState();
			ItemStack itemStack = context.getItemStack();
			Item item = context.getItem();
			if (chestBlockEntity.viewers() == 0) {
				if (blockState.get(GenericChestBlock.LOCK).equals(LockState.NO_LOCK)) {
					if (item instanceof LockItem) {
						return InteractionType.USE_ITEM_ON_BLOCK;
					}
				} else {
					if (item instanceof KeyItem) {
						if (itemStack.hasNbt() && itemStack.getNbt().contains("key")) {
							if (chestBlockEntity.isRightKey(itemStack.getNbt().getUuid("key"))) {
								return InteractionType.USE_ITEM_ON_BLOCK;
							}
						} else {
							if (blockState.get(GenericChestBlock.LOCK).equals(LockState.UNLOCKED)) {
								return InteractionType.USE_ITEM_ON_BLOCK;
							}
						}
					}
				}
			}
		}
		return InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
