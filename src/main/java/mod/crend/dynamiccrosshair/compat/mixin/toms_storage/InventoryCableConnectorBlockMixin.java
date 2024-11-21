//? if toms-storage-fabric {
package mod.crend.dynamiccrosshair.compat.mixin.toms_storage;

import com.tom.storagemod.block.InventoryCableConnectorBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = InventoryCableConnectorBlock.class, remap = false)
public class InventoryCableConnectorBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		Direction f = context.getBlockState().get(InventoryCableConnectorBlock.FACING);
		BlockState pointedAt = context.getWorld().getBlockState(context.getBlockPos().offset(f));
		if (context.getItemStack().isEmpty() && pointedAt.isOf(Blocks.BEACON)) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
