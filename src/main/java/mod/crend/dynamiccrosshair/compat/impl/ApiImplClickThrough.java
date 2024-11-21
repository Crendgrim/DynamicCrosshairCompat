package mod.crend.dynamiccrosshair.compat.impl;

//? if clickthrough {
import de.guntram.mcmod.clickthrough.ClickThrough;
import de.guntram.mcmod.clickthrough.ConfigurationHandler;
//?}
import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.exception.CrosshairContextChange;
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
		return "clickthrough";
	}

	//? if clickthrough {

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		if (context.getEntity() instanceof ItemFrameEntity itemFrame && !context.getPlayer().isSneaking()) {
			BlockPos attachedPos = itemFrame.getDecorationBlockPos().offset(itemFrame.getHorizontalFacing().getOpposite());
			if (isClickableBlockAt(context, attachedPos)) {
				throw new CrosshairContextChange(new BlockHitResult(context.getHitResult().getPos(), itemFrame.getHorizontalFacing(), attachedPos, false));
			}
		}

		return null;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		BlockPos blockPos = context.getBlockPos();

		if (block instanceof WallSignBlock && context.getBlockEntity() instanceof SignBlockEntity signBlockEntity && context.isMainHand() && !context.getPlayer().isSneaking()) {
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
				throw new CrosshairContextChange(new BlockHitResult(context.getHitResult().getPos(), context.getBlockHitSide(), attachedPos, false));
			}
		} else if (block instanceof WallBannerBlock) {
			BlockPos attachedPos = blockPos.offset(blockState.get(WallBannerBlock.FACING).getOpposite());
			if (isClickableBlockAt(context, attachedPos)) {
				throw new CrosshairContextChange(new BlockHitResult(context.getHitResult().getPos(), context.getBlockHitSide(), attachedPos, false));
			}
		}

		return null;
	}

	// Copied from ClickThrough
	private boolean isClickableBlockAt(CrosshairContext context, BlockPos pos) {
        if (!ConfigurationHandler.onlyToContainers()) {
            return true;
        }
        BlockEntity entity = context.getWorld().getBlockEntity(pos);
        return (entity instanceof LockableContainerBlockEntity);
    }
	//?}
}
