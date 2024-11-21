//? if mariums-soulslike-weaponry {
package mod.crend.dynamiccrosshair.compat.mixin.soulsweapons;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.soulsweaponry.blocks.AltarBlock;
import net.soulsweaponry.registry.ItemRegistry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = AltarBlock.class, remap = false)
public class AltarBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItemStack().isOf(ItemRegistry.LOST_SOUL)) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
