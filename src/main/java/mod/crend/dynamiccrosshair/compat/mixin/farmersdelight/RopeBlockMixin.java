//? if farmers-delight-refabricated {
package mod.crend.dynamiccrosshair.compat.mixin.farmersdelight;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.block.RopeBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;

@Mixin(value = RopeBlock.class, remap = false)
public class RopeBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItemStack().isEmpty()) {
			BlockPos pos = context.getBlockPos();
			BlockPos.Mutable mutablePos;
			int minBuildHeight;
			BlockState blockState;
			if (Configuration.ENABLE_ROPE_REELING.get() && context.getPlayer().shouldCancelInteraction()) {
				if (context.getPlayer().getAbilities().allowModifyWorld) {
					mutablePos = pos.mutableCopy().move(Direction.DOWN);
					minBuildHeight = context.getWorld().getBottomY();

					while (mutablePos.getY() >= minBuildHeight) {
						blockState = context.getWorld().getBlockState(mutablePos);
						if (!blockState.isOf(context.getBlock())) {
							return InteractionType.INTERACT_WITH_BLOCK;
						}

						mutablePos.move(Direction.DOWN);
					}
				}
			} else {
				mutablePos = pos.mutableCopy().move(Direction.UP);

				for (minBuildHeight = 0; minBuildHeight < 24; ++minBuildHeight) {
					blockState = context.getWorld().getBlockState(mutablePos);
					Block blockAbove = blockState.getBlock();
					if (blockAbove == Blocks.BELL) {
						return InteractionType.INTERACT_WITH_BLOCK;
					}

					if (blockAbove != ModBlocks.ROPE.get()) {
						return InteractionType.EMPTY;
					}

					mutablePos.move(Direction.UP);
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
