//? if waystones {
package mod.crend.dynamiccrosshair.compat.mixin.fwaystones;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import wraith.fwaystones.block.WaystoneBlock;
import wraith.fwaystones.item.WaystoneDebuggerItem;

@Mixin(value = WaystoneDebuggerItem.class, remap = false)
public class WaystoneDebuggerItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock() && context.getBlock() instanceof WaystoneBlock) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		if (context.isWithEntity() && context.getEntity() instanceof PlayerEntity) {
			return InteractionType.USE_ITEM_ON_ENTITY;
		}
		return InteractionType.EMPTY;
	}
}
//?}
