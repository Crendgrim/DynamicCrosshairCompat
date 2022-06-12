package mod.crend.dynamiccrosshair.compat.chalk;

import de.dafuqs.chalk.chalk.Chalk;
import de.dafuqs.chalk.chalk.blocks.ChalkMarkBlock;
import de.dafuqs.chalk.chalk.items.ChalkItem;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IBlockItemHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class ApiImplChalk implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Chalk.MOD_ID;
	}

	@Override
	public IBlockItemHandler getBlockItemHandler() {
		return (clientPlayerEntity, itemStack) -> {
			if (itemStack.getItem() instanceof ChalkItem) {
				HitResult hitResult = MinecraftClient.getInstance().crosshairTarget;
				if (hitResult.getType() == HitResult.Type.BLOCK) {
					ClientWorld world = MinecraftClient.getInstance().world;
					BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
					BlockState blockState = world.getBlockState(blockPos);
					Block block = blockState.getBlock();
					if (block instanceof ChalkMarkBlock) {
						return Crosshair.HOLDING_BLOCK;
					}
					Direction clickedFace = ((BlockHitResult) hitResult).getSide();
					if (Block.isFaceFullSquare(blockState.getCollisionShape(world, blockPos, ShapeContext.of(clientPlayerEntity)), clickedFace)) {
						BlockPos chalkPos = blockPos.offset(clickedFace);
						if (world.getBlockState(chalkPos).getMaterial().isReplaceable()) {
							return Crosshair.HOLDING_BLOCK;
						}
					}
				}
			}
			return null;
		};
	}
}
