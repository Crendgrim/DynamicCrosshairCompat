//? if carrier {
package mod.crend.dynamiccrosshair.compat.mixin.carrier;

import me.steven.carrier.api.CarriableRegistry;
import me.steven.carrier.items.GloveItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = GloveItem.class, remap = false)
public class GloveItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isMainHand()) {
			if (context.isWithEntity() && CarriableRegistry.INSTANCE.contains(context.getEntity().getType())) {
				return InteractionType.USE_ITEM_ON_ENTITY;
			}
			if (context.isWithBlock() && context.includeUsableItem()) {
				BlockState blockState = context.getBlockState();
				if (CarriableRegistry.INSTANCE.contains(blockState.getBlock()) && blockState.getHardness(context.getWorld(), context.getBlockPos()) > -1.0F) {
					return InteractionType.USE_ITEM_ON_BLOCK;
				}
			}
		}

		return InteractionType.EMPTY;
	}
}
//?}
