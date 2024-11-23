//? if another-furniture {
package mod.crend.dynamiccrosshair.compat.mixin.another_furniture;

import com.starfish_studios.another_furniture.block.ChairBlock;
import com.starfish_studios.another_furniture.block.SeatBlock;
import com.starfish_studios.another_furniture.entity.SeatEntity;
import com.starfish_studios.another_furniture.registry.AFBlockTags;
import com.starfish_studios.another_furniture.util.block.TuckableBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = SeatBlock.class, remap = false)
public abstract class SeatBlockMixin implements DynamicCrosshairBlock {
	@Shadow public abstract boolean isSittable(BlockState state);

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		BlockPos pos = context.getBlockPos();
		BlockState above = context.getWorld().getBlockState(pos.up());
		if (this instanceof TuckableBlock) {
			BlockState blockState = context.getBlockState();
			boolean tucked = blockState.get(ChairBlock.TUCKED);
			if ((context.getPlayer().isInSneakingPose() || tucked) && TuckableBlock.canTuckUnderBlockInfront(blockState, context.getWorld(), pos)) {
				if (tucked || TuckableBlock.isBlockedFromTucking(blockState, context.getWorld(), pos)) {
					return InteractionType.INTERACT_WITH_BLOCK;
				}
			}
		}
		if (isSittable(context.getBlockState())
				&& !context.getPlayer().hasVehicle()
				&& !context.getPlayer().isInSneakingPose()
				&& (above.getCollisionShape(context.getWorld(), pos).isEmpty() || above.isIn(AFBlockTags.ABOVE_BYPASSES_SEAT_CHECK))
				&& context.getWorld().getNonSpectatingEntities(
						SeatEntity.class,
						//? if <1.21.1 {
						new Box(pos, pos.add(1, 1, 1))
						//?} else
						/*new Box(pos)*/
				).isEmpty()
		) {
			return InteractionType.MOUNT_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
