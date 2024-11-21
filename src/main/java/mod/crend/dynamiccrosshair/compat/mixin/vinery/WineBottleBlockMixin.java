//? if vinery {
package mod.crend.dynamiccrosshair.compat.mixin.vinery;

import de.cristelknight.doapi.common.block.entity.StorageBlockEntity;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import satisfyu.vinery.block.storage.WineBottleBlock;

@Mixin(value = WineBottleBlock.class, remap = false)
public abstract class WineBottleBlockMixin implements DynamicCrosshairBlock {

	@Shadow public abstract boolean canInsertStack(ItemStack itemStack);
	@Shadow public abstract int getFirstEmptySlot(DefaultedList<ItemStack> inventory);
	@Shadow public abstract int getLastFullSlot(DefaultedList<ItemStack> inventory);
	@Shadow public abstract boolean isEmpty(DefaultedList<ItemStack> inventory);
	@Shadow public abstract boolean willFitStack(ItemStack itemStack, DefaultedList<ItemStack> inventory);

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof StorageBlockEntity wineEntity) {
			ItemStack itemStack = context.getItemStack();
			DefaultedList<ItemStack> inventory = wineEntity.getInventory();
			if (this.canInsertStack(itemStack) && this.willFitStack(itemStack, inventory)) {
				if (this.getFirstEmptySlot(inventory) != Integer.MIN_VALUE) {
					return InteractionType.PLACE_ITEM_ON_BLOCK;
				}
			}
			else if (itemStack.isEmpty() && !this.isEmpty(inventory)) {
				if (this.getLastFullSlot(inventory) != Integer.MIN_VALUE) {
					return InteractionType.TAKE_ITEM_FROM_BLOCK;
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
