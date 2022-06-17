package mod.crend.dynamiccrosshair.compat.botania;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import vazkii.botania.api.block.IFloatingFlower;
import vazkii.botania.api.mana.IManaCollector;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.common.item.*;
import vazkii.botania.common.item.relic.ItemFlugelEye;
import vazkii.botania.common.item.relic.ItemInfiniteFruit;
import vazkii.botania.common.item.relic.ItemKingKey;
import vazkii.botania.common.item.rod.*;
import vazkii.botania.xplat.IXplatAbstractions;

class BotaniaUsableItemHandler {
	public static boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (   item instanceof ItemWorldSeed
				|| item instanceof ItemLexicon
				|| item instanceof ItemTwigWand
				|| item instanceof ItemHorn
				|| item instanceof ItemEnderHand
				|| item instanceof ItemFlowerBag
				|| item instanceof ItemBaubleBox
				|| item instanceof ItemAstrolabe
				|| item instanceof ItemSextant
				|| item instanceof ItemFlugelEye
				|| item instanceof ItemInfiniteFruit
				|| item instanceof ItemKingKey
				|| item instanceof ItemTerraformRod
				|| item instanceof ItemDiviningRod
				|| item instanceof ItemSmeltRod
				|| item instanceof ItemRainbowRod
				|| item instanceof ItemGravityRod
		);
	}
	public static boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (   item instanceof ItemGrassSeeds
				|| item instanceof ItemOvergrowthSeed
				|| item instanceof ItemSpawnerMover
				|| item instanceof ItemBlackHoleTalisman
				|| item instanceof ItemCacophonium
				|| item instanceof ItemObedienceStick
				|| item instanceof ItemDirtRod // Dirt Rod, Sky Dirt Rod
				|| item instanceof ItemCobbleRod
				|| item instanceof ItemWaterRod
				|| item instanceof ItemTornadoRod
				|| item instanceof ItemFireRod
				|| item instanceof ItemExchangeRod
		);
	}

	public static Crosshair checkUsableItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof ItemTornadoRod) {
			if (!item.isItemBarVisible(itemStack)) {
				return Crosshair.USE_ITEM;
			}
		}

		if (context.isWithBlock()) {
			return checkUsableItemOnBlock(context);
		}

		if (!context.isTargeting()) {
			return checkUsableItemOnMiss(context);
		}

		return null;
	}

	public static Crosshair checkUsableItemOnBlock(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();
		BlockState blockState = context.getBlockState();
		if (item instanceof ItemBlackHoleTalisman
				|| item instanceof ItemFireRod
				|| item instanceof ItemExchangeRod
		) {
			return Crosshair.USE_ITEM;
		}
		if (item instanceof ItemObedienceStick) {
			BlockPos blockPos = context.getBlockPos();
			IManaReceiver receiver = IXplatAbstractions.INSTANCE.findManaReceiver(context.world, blockPos, blockState, context.world.getBlockEntity(blockPos), null);
			if (receiver instanceof IManaPool || receiver instanceof IManaCollector) {
				return Crosshair.USE_ITEM;
			}
		}
		if (item instanceof ItemGrassSeeds) {
			if (blockState.isOf(Blocks.DIRT) || (blockState.isOf(Blocks.GRASS_BLOCK) && ((ItemGrassSeeds) item).getIslandType(itemStack) != IFloatingFlower.IslandType.GRASS)) {
				return Crosshair.USE_ITEM;
			}
		}
		if (item instanceof ItemOvergrowthSeed) {
			if (blockState.isOf(Blocks.GRASS_BLOCK)) {
				return Crosshair.USE_ITEM;
			}
		}
		if (item instanceof ItemCacophonium) {
			if (blockState.isOf(Blocks.NOTE_BLOCK)) {
				return Crosshair.INTERACTABLE;
			}
			return Crosshair.USE_ITEM;
		}
		if (item instanceof ItemSpawnerMover) {
			if (ItemSpawnerMover.hasData(itemStack)) {
				return Crosshair.HOLDING_BLOCK;
			} else {
				if (blockState.getBlock() instanceof SpawnerBlock) {
					return Crosshair.USE_ITEM;
				}
			}
		}

		if (item instanceof ItemDirtRod
				|| item instanceof ItemCobbleRod
				|| item instanceof ItemWaterRod
		) {
			return Crosshair.HOLDING_BLOCK;
		}

		return null;
	}

	public static Crosshair checkUsableItemOnMiss(CrosshairContext context) {
		Item item = context.getItem();

		if (item instanceof ItemBlackHoleTalisman) {
			if (context.player.shouldCancelInteraction()) {
				return Crosshair.USE_ITEM;
			}
		}
		if (item instanceof ItemCacophonium) {
			return Crosshair.USE_ITEM;
		}

		if (item instanceof ItemSkyDirtRod) {
			return Crosshair.HOLDING_BLOCK;
		}

		return null;
	}
}
