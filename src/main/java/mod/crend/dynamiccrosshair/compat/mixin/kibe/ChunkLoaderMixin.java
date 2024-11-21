//? if kibe {
package mod.crend.dynamiccrosshair.compat.mixin.kibe;

import io.github.lucaargolo.kibe.blocks.chunkloader.ChunkLoader;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.state.property.Properties;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ChunkLoader.class, remap = false)
public class ChunkLoaderMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return context.getBlockState().get(Properties.ENABLED) ? InteractionType.INTERACT_WITH_BLOCK : InteractionType.EMPTY;
	}
}
//?}
