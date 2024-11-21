//? if bewitchment {
package mod.crend.dynamiccrosshair.compat.mixin.bewitchment;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import moriyashiine.bewitchment.common.block.SaltLineBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = SaltLineBlock.class, remap = false)
public abstract class SaltLineBlockMixin implements DynamicCrosshairBlock {
	@Shadow
	private static boolean isFullyConnected(BlockState state) {
		return false;
	}

	@Shadow
	private static boolean isNotConnected(BlockState state) {
		return false;
	}

	@Shadow protected abstract BlockState getPlacementState(BlockView world, BlockPos pos);

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		if (context.getPlayer().getAbilities().allowModifyWorld
				&& (isFullyConnected(blockState) || isNotConnected(blockState))) {
			BlockState placementState = getPlacementState(context.getWorld(), context.getBlockPos());
			if (placementState != blockState) {
				return InteractionType.INTERACT_WITH_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
