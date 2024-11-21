//? if toms-storage-fabric {
package mod.crend.dynamiccrosshair.compat.mixin.toms_storage;

import com.tom.storagemod.StorageMod;
import com.tom.storagemod.StorageTags;
import com.tom.storagemod.item.WirelessTerminalItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.block.BlockState;
import net.minecraft.util.hit.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = WirelessTerminalItem.class, remap = false)
public class WirelessTerminalItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		BlockHitResult lookingAt = (BlockHitResult)context.getPlayer().raycast(StorageMod.CONFIG.wirelessRange, 0.0F, true);
		BlockState state = context.getWorld().getBlockState(lookingAt.getBlockPos());
		if (state.isIn(StorageTags.REMOTE_ACTIVATE)) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
