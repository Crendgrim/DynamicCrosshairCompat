//? if kibe {
package mod.crend.dynamiccrosshair.compat.mixin.kibe;

import io.github.lucaargolo.kibe.items.miscellaneous.CursedSeeds;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = CursedSeeds.class, remap = false)
public class CursedSeedsMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock()) {
			Block block = context.getBlock();
			if ((block == Blocks.DIRT || block == Blocks.GRASS_BLOCK)
					&& context.getWorld().getLightLevel(context.getBlockPos()) <= 7) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
