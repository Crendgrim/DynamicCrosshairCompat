//? if dark-utilities {
package mod.crend.dynamiccrosshair.compat.mixin.darkutils;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.darkhax.darkutilities.features.flatblocks.BlockFlatTile;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BlockFlatTile.class, remap = false)
public class BlockFlatTileMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItemStack().isOf(Items.REDSTONE_TORCH)) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
