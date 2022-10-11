package mod.crend.dynamiccrosshair.compat.decorative_blocks;

import lilypuree.decorative_blocks.blocks.SeatBlock;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.mixin.decorative_blocks.ISeatBlockMixin;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HayBlock;
import net.minecraft.block.LanternBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;

public class ApiImplDecorativeBlocks implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "decorative_blocks";
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair checkBlockItem(CrosshairContext context) {
		// attach lantern to seat
		if (context.isWithBlock()
				&& context.getItem() instanceof BlockItem blockItem
				&& blockItem.getBlock() instanceof LanternBlock
				&& context.getBlock() instanceof SeatBlock
				&& ((BlockHitResult) context.hitResult).getSide() == Direction.DOWN
				&& context.world.getBlockState(context.getBlockPos().down()).isAir()
		) {
			return Crosshair.HOLDING_BLOCK;
		}
		return null;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		BlockState state = context.getBlockState();
		Block block = context.getBlock();
		ItemStack heldItem = context.getItemStack();

		// sit on seat
		if (block instanceof SeatBlock
				&& ((BlockHitResult) context.hitResult).getSide() == Direction.UP
				&& !state.get(SeatBlock.OCCUPIED)
				&& !state.get(SeatBlock.POST)
				&& heldItem.isEmpty()
				&& context.world.getBlockState(context.getBlockPos().up()).isAir()
				&& ISeatBlockMixin.invokeIsPlayerInRange(context.player, context.getBlockPos())
		) {
			return Crosshair.INTERACTABLE;
		}

		if (block instanceof HayBlock) {
			if (heldItem.getItem() instanceof ShearsItem) {
				return Crosshair.USE_ITEM;
			}
		}

		return null;
	}
}
