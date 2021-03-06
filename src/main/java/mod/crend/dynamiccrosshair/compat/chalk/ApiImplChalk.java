package mod.crend.dynamiccrosshair.compat.chalk;

import de.dafuqs.chalk.chalk.Chalk;
import de.dafuqs.chalk.chalk.blocks.ChalkMarkBlock;
import de.dafuqs.chalk.chalk.items.ChalkItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class ApiImplChalk implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Chalk.MOD_ID;
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		if (context.isWithBlock() && context.getItem() instanceof ChalkItem) {
			if (context.getBlock() instanceof ChalkMarkBlock) {
				return Crosshair.HOLDING_BLOCK;
			}
			Direction clickedFace = ((BlockHitResult) context.hitResult).getSide();
			BlockPos blockPos = context.getBlockPos();
			BlockState blockState = context.getBlockState();
			if (Block.isFaceFullSquare(blockState.getCollisionShape(context.world, blockPos, ShapeContext.of(context.player)), clickedFace)) {
				BlockPos chalkPos = blockPos.offset(clickedFace);
				if (context.world.getBlockState(chalkPos).getMaterial().isReplaceable()) {
					return Crosshair.HOLDING_BLOCK;
				}
			}
		}
		return null;
	}
}
