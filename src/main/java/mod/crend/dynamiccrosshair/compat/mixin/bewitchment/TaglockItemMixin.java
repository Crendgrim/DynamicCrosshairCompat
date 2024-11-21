//? if bewitchment {
package mod.crend.dynamiccrosshair.compat.mixin.bewitchment;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import moriyashiine.bewitchment.common.block.entity.interfaces.Lockable;
import moriyashiine.bewitchment.common.block.entity.interfaces.SigilHolder;
import moriyashiine.bewitchment.common.block.entity.interfaces.TaglockHolder;
import moriyashiine.bewitchment.common.item.TaglockItem;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import java.util.UUID;

@Mixin(value = TaglockItem.class, remap = false)
public class TaglockItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock()) {
			BlockState blockState = context.getBlockState();
			if (blockState.getBlock() instanceof BedBlock) {
				if (context.getPlayer().isSneaking()) {
					return InteractionType.USE_ITEM_ON_BLOCK;
				}
			} else {
				ItemStack stack = context.getItemStack();
				BlockEntity blockEntity;
				if (blockState.getBlock() instanceof DoorBlock && blockState.get(DoorBlock.HALF) == DoubleBlockHalf.UPPER) {
					blockEntity = context.getWorld().getBlockEntity(context.getBlockPos().down());
				} else {
					blockEntity = context.getBlockEntity();
				}
				if (blockEntity instanceof TaglockHolder taglockHolder) {
					if (context.getPlayer().getUuid().equals(taglockHolder.getOwner())) {
						int firstEmpty = taglockHolder.getFirstEmptySlot();
						if (firstEmpty != -1) {
							return InteractionType.USE_ITEM_ON_BLOCK;
						}
					}
				} else {
					UUID uuid;
					if (blockEntity instanceof Lockable lockable) {
						if (TaglockItem.hasTaglock(stack) && context.getPlayer().getUuid().equals(lockable.getOwner()) && lockable.getLocked()) {
							uuid = TaglockItem.getTaglockUUID(stack);
							if (!lockable.getEntities().contains(uuid)) {
								return InteractionType.USE_ITEM_ON_BLOCK;
							}
						}
					} else if (blockEntity instanceof SigilHolder) {
						if (TaglockItem.hasTaglock(stack)) {
							return InteractionType.USE_ITEM_ON_BLOCK;
						}
					}
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
