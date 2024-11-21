//? if industrial-revolution {
package mod.crend.dynamiccrosshair.compat.mixin.indrev;

import me.steven.indrev.blockentities.cables.BasePipeBlockEntity;
import me.steven.indrev.blocks.machine.pipes.BasePipeBlock;
import me.steven.indrev.blocks.machine.pipes.ItemPipeBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ItemPipeBlock.class, remap = false)
public class ItemPipeBlockMixin extends BasePipeBlockMixin {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof BasePipeBlockEntity pipeBlockEntity) {
			Direction dir = BasePipeBlock.Companion.getSideFromHit(context.getHitResult().getPos(), context.getBlockPos());
			if (context.isEmptyHanded()
					&& dir != null
					&& pipeBlockEntity.getConnections().getOrDefault(dir, BasePipeBlock.ConnectionType.NONE).isConnected()
			) {
				return InteractionType.INTERACT_WITH_BLOCK;
			}
		}
		return super.dynamiccrosshair$compute(context);
	}
}
//?}
