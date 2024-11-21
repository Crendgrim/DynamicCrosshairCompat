//? if mariums-soulslike-weaponry {
package mod.crend.dynamiccrosshair.compat.mixin.soulsweapons;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.soulsweaponry.blocks.BlackstonePedestal;
import net.soulsweaponry.registry.ItemRegistry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BlackstonePedestal.class, remap = false)
public class BlackstonePedestalMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItemStack().isOf(ItemRegistry.SHARD_OF_UNCERTAINTY)) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
