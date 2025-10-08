package mod.crend.dynamiccrosshair.compat.impl;

//? if clickthrough {
import cc.cassian.clickthrough.config.ModConfig;
import cc.cassian.clickthrough.helpers.ModHelpers;
//?}
import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.exception.CrosshairContextChange;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBannerBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;

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
			BlockPos attachedPos = itemFrame./*? if <1.21 {*/getDecorationBlockPos()/*?} else {*//*getAttachedBlockPos()*//*?}*/.offset(itemFrame.getHorizontalFacing().getOpposite());
			if (ModHelpers.isClickableBlockAt(attachedPos, context.getWorld())) {
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

		if (block instanceof WallSignBlock && context.isMainHand() && !context.getPlayer().isSneaking()) {
			BlockPos attachedPos = blockPos.offset(blockState.get(WallSignBlock.FACING).getOpposite());
			if (!ModHelpers.isClickableBlockAt(attachedPos, context.getWorld())) {
				return null;
			}

			if (!(context.getItem() instanceof DyeItem || context.getItem().equals(Items.GLOW_INK_SAC))
					|| ModConfig.get().sneaktodye) {
				throw new CrosshairContextChange(new BlockHitResult(context.getHitResult().getPos(), context.getBlockHitSide(), attachedPos, false));
			}
		} else if (block instanceof WallBannerBlock) {
			BlockPos attachedPos = blockPos.offset(blockState.get(WallBannerBlock.FACING).getOpposite());
			if (ModHelpers.isClickableBlockAt(attachedPos, context.getWorld())) {
				throw new CrosshairContextChange(new BlockHitResult(context.getHitResult().getPos(), context.getBlockHitSide(), attachedPos, false));
			}
		}

		return null;
	}

	//?}
}
