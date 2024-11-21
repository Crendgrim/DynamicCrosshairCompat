//? if toms-storage-fabric {
package mod.crend.dynamiccrosshair.compat.mixin.toms_storage;

import com.tom.storagemod.block.IPaintable;
import com.tom.storagemod.item.PaintKitItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = PaintKitItem.class, remap = false)
public class PaintKitItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock()) {
			if (context.getPlayer().shouldCancelInteraction() || context.getBlock() instanceof IPaintable) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
