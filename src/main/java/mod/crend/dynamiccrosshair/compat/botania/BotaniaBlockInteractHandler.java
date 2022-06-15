package mod.crend.dynamiccrosshair.compat.botania;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.IBlockInteractHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import vazkii.botania.api.block.IPetalApothecary;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.common.block.BlockAltar;
import vazkii.botania.common.block.BlockHourglass;
import vazkii.botania.common.block.mana.BlockBrewery;
import vazkii.botania.common.block.mana.BlockPool;
import vazkii.botania.common.block.mana.BlockSpreader;
import vazkii.botania.common.block.tile.TileBrewery;
import vazkii.botania.common.block.tile.TileHourglass;
import vazkii.botania.common.block.tile.mana.TilePool;
import vazkii.botania.common.block.tile.mana.TileSpreader;
import vazkii.botania.common.helper.ColorHelper;
import vazkii.botania.common.item.ItemManaSpark;
import vazkii.botania.common.item.lens.ItemLens;

class BotaniaBlockInteractHandler implements IBlockInteractHandler {

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack itemStack = context.getItemStack();

		if (block instanceof BlockAltar && context.getBlockEntity() instanceof IPetalApothecary blockEntity) {
			if (blockEntity.getFluid() == IPetalApothecary.State.EMPTY) {
				if (itemStack.isOf(Items.WATER_BUCKET) || itemStack.isOf(Items.LAVA_BUCKET)) {
					return Crosshair.USE_ITEM;
				}
			} else {
				if (itemStack.isOf(Items.BUCKET)) {
					return Crosshair.USE_ITEM;
				}
			}
		}

		if (block instanceof BlockPool) {
			Item item = itemStack.getItem();
			if (item instanceof DyeItem de && context.getBlockEntity() instanceof TilePool pool) {
				if (de.getColor() != pool.getColor()) {
					return Crosshair.USE_ITEM;
				}
			}
			if (item instanceof ItemManaSpark) {
				return Crosshair.HOLDING_BLOCK;
			}
		}
		if (block instanceof BlockSpreader && context.getBlockEntity() instanceof TileSpreader blockEntity) {
			Item item = itemStack.getItem();
			if (item instanceof ItemLens) {
				// lens installed?
				if (blockEntity.getItemHandler().getStack(0).isEmpty()) {
					return Crosshair.USE_ITEM;
				}
			} else if (ColorHelper.isWool(block)) {
				if (blockEntity.paddingColor == null) {
					return Crosshair.USE_ITEM;
				}
			} else if (item.equals(Items.SCAFFOLDING)) {
				if (!blockState.get(BotaniaStateProps.HAS_SCAFFOLDING)) {
					return Crosshair.USE_ITEM;
				}
			}
			if (blockState.get(BotaniaStateProps.HAS_SCAFFOLDING) && context.player.shouldCancelInteraction()) {
				return Crosshair.INTERACTABLE;
			}
			if (ColorHelper.isWool(block) || !blockEntity.getItemHandler().getStack(0).isEmpty()) {
				return Crosshair.INTERACTABLE;
			}
		}

		if (block instanceof BlockHourglass && context.getBlockEntity() instanceof TileHourglass hourglass) {
			if (hourglass.isEmpty()) {
				if (itemStack.isOf(Items.SAND) || itemStack.isOf(Items.RED_SAND)) {
					return Crosshair.USE_ITEM;
				}
			} else {
				return Crosshair.INTERACTABLE;
			}
		}

		if (block instanceof BlockBrewery && context.getBlockEntity() instanceof TileBrewery brewery) {
			if (context.player.shouldCancelInteraction()) {
				if (brewery.recipe == null && !blockState.get(Properties.POWERED)) {
					return Crosshair.INTERACTABLE;
				}
			} else {
				return Crosshair.USE_ITEM;
			}
		}

		return null;
	}
}
