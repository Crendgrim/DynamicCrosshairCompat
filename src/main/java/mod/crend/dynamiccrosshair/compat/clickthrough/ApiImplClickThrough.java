package mod.crend.dynamiccrosshair.compat.clickthrough;

import de.guntram.mcmod.clickthrough.ClickThrough;
import de.guntram.mcmod.clickthrough.ConfigurationHandler;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.CrosshairContextChange;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBannerBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;

import java.util.regex.Pattern;

public class ApiImplClickThrough implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return ClickThrough.MODID;
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		if (context.getEntity() instanceof ItemFrameEntity itemFrame && !context.player.isSneaking()) {
			BlockPos attachedPos = itemFrame.getDecorationBlockPos().offset(itemFrame.getHorizontalFacing().getOpposite());
			if (isClickableBlockAt(context, attachedPos)) {
				throw new CrosshairContextChange(new BlockHitResult(context.hitResult.getPos(), itemFrame.getHorizontalFacing(), attachedPos, false));
			}
		}

		return null;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		BlockPos blockPos = context.getBlockPos();

		if (block instanceof WallSignBlock && context.getBlockEntity() instanceof SignBlockEntity signBlockEntity && context.isMainHand() && !context.player.isSneaking()) {
			BlockPos attachedPos = blockPos.offset(blockState.get(WallSignBlock.FACING).getOpposite());
			if (!isClickableBlockAt(context, attachedPos)) {
				return null;
			}

			// Copied from ClickThrough
			for (int i=0; i<4; i++) {
				Pattern pattern;
				if ((pattern = ConfigurationHandler.getIgnorePattern(i)) != null) {
					String signText = ClickThrough.getSignRowText(signBlockEntity, i);
					if (pattern.matcher(signText).matches()) {
						return null;
					}
				}
			}

			if (!(context.getItem() instanceof DyeItem) || ConfigurationHandler.getSneakToDyeSigns()) {
				throw new CrosshairContextChange(new BlockHitResult(context.hitResult.getPos(), context.getBlockHitSide(), attachedPos, false));
			}
		} else if (block instanceof WallBannerBlock) {
			BlockPos attachedPos = blockPos.offset(blockState.get(WallBannerBlock.FACING).getOpposite());
			if (isClickableBlockAt(context, attachedPos)) {
				throw new CrosshairContextChange(new BlockHitResult(context.hitResult.getPos(), context.getBlockHitSide(), attachedPos, false));
			}
		}

		return null;
	}

	// Copied from ClickThrough
	private boolean isClickableBlockAt(CrosshairContext context, BlockPos pos) {
        if (!ConfigurationHandler.onlyToContainers()) {
            return true;
        }
        BlockEntity entity = context.world.getBlockEntity(pos);
        return (entity instanceof LockableContainerBlockEntity);
    }
}
