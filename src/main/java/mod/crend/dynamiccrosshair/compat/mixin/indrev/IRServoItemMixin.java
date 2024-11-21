//? if industrial-revolution {
package mod.crend.dynamiccrosshair.compat.mixin.indrev;

import me.steven.indrev.blockentities.cables.BasePipeBlockEntity;
import me.steven.indrev.blocks.machine.pipes.BasePipeBlock;
import me.steven.indrev.items.misc.IRServoItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = IRServoItem.class, remap = false)
public class IRServoItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (!context.isWithBlock()) {
			return InteractionType.USE_ITEM;
		}
		if (context.isMainHand() && context.getBlockEntity() instanceof BasePipeBlockEntity pipeBlockEntity) {
			Direction dir = BasePipeBlock.Companion.getSideFromHit(context.getHitResult().getPos(), context.getBlockPos());
			if (dir != null && pipeBlockEntity.getConnections().getOrDefault(dir, BasePipeBlock.ConnectionType.NONE).isConnected()) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
