//? if toms-storage-fabric {
package mod.crend.dynamiccrosshair.compat.mixin.toms_storage;

import com.tom.storagemod.StorageTags;
import com.tom.storagemod.item.AdvWirelessTerminalItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = AdvWirelessTerminalItem.class, remap = false)
public class AdvWirelessTerminalItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (context.isWithBlock() && context.getPlayer().shouldCancelInteraction()) {
			if (context.getBlockState().isIn(StorageTags.REMOTE_ACTIVATE)) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		} else if (itemStack.hasNbt() && itemStack.getNbt().contains("BindX")) {
			return InteractionType.USE_ITEM;
		}
		return InteractionType.EMPTY;
	}
}
//?}
