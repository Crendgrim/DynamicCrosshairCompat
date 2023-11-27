package mod.crend.dynamiccrosshair.compat.twigs;

import com.ninni.twigs.Twigs;
import com.ninni.twigs.block.LampBlock;
import com.ninni.twigs.block.enums.SiltPotBlock;
import com.ninni.twigs.item.InstrumentBlockItem;
import com.ninni.twigs.item.PebbleItem;
import com.ninni.twigs.item.TwigItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.ItemCategory;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;

public class ApiImplTwigs implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Twigs.MOD_ID;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof LampBlock || block instanceof SiltPotBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();

		if (block instanceof LampBlock && !context.player.isSneaking()) {
			return Crosshair.INTERACTABLE;
		}

		if (block instanceof SiltPotBlock) {
			ItemStack stack = context.getItemStack();
			if (!blockState.get(SiltPotBlock.FILLED)) {
				if (stack.isOf(Blocks.ROOTED_DIRT.asItem())) {
					return Crosshair.USABLE;
				}
				return Crosshair.INTERACTABLE;
			} else {
				if (context.getBlockHitSide() == Direction.UP
						&& stack.getItem() instanceof BlockItem blockItem
						&& blockItem.getBlock() instanceof FlowerBlock
				) {
					return Crosshair.HOLDING_BLOCK;
				}

				if (stack.getItem() instanceof ShovelItem) {
					return Crosshair.USABLE;
				}
			}
		}

		return null;
	}

	@Override
	public ItemCategory getItemCategory(ItemStack itemStack) {
		if (itemStack.getItem() instanceof PebbleItem) {
			return ItemCategory.THROWABLE;
		}
		return DynamicCrosshairApi.super.getItemCategory(itemStack);
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof InstrumentBlockItem;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof TwigItem;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		Item item = context.getItem();

		if (item instanceof PebbleItem) {
			return Crosshair.THROWABLE;
		}

		if (item instanceof TwigItem) {
			if (context.getItemStack(Hand.OFF_HAND).isOf(context.getItemStack(Hand.MAIN_HAND).getItem())) {
				return Crosshair.HOLDING_BLOCK;
			}
		}

		return null;
	}
}
