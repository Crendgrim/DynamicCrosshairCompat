//? if dark-utilities {
package mod.crend.dynamiccrosshair.compat.mixin.darkutils;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.darkhax.darkutilities.features.flatblocks.BlockFlatTileRotatable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BlockFlatTileRotatable.class, remap = false)
public class BlockFlatTileRotatableMixin extends BlockFlatTileMixin {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getPlayer().isSneaking()) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		return super.dynamiccrosshair$compute(context);
	}
}
//?}
