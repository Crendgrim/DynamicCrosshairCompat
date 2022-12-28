package mod.crend.dynamiccrosshair.compat.byg;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import potionstudios.byg.BYG;
import potionstudios.byg.common.block.*;
import potionstudios.byg.common.block.end.TheriumCrystalBlock;
import potionstudios.byg.common.block.end.nightshade.NightshadeBerryBushBlock;
import potionstudios.byg.common.block.nether.HypogealImperiumBlock;
import potionstudios.byg.common.block.nether.crimson.CrimsonBerryBushBlock;
import potionstudios.byg.common.item.BYGLilyItem;
import potionstudios.byg.common.item.BYGWaterSilkItem;
import potionstudios.byg.common.item.BiomepediaItem;
import potionstudios.byg.common.item.CampfireExplodingBlockItem;

public class ApiImplOhTheBiomesYoullGo implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return BYG.MOD_ID;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof HypogealImperiumBlock;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof NightshadeBerryBushBlock
				|| block instanceof TheriumCrystalBlock
				;
	}

	public Crosshair fromBush(BlockState blockState, ItemStack itemStack, IntProperty ageProperty, int ageToHarvest) {
		int age = blockState.get(ageProperty);
		if (age != 3 && itemStack.isOf(Items.BONE_MEAL)) {
			return Crosshair.USABLE;
		} else if (age > ageToHarvest) {
			return Crosshair.INTERACTABLE;
		}
		return null;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack itemStack = context.getItemStack();

		if (block instanceof NightshadeBerryBushBlock) {
			return fromBush(blockState, itemStack, NightshadeBerryBushBlock.AGE, 1);
		}
		if (block instanceof TheriumCrystalBlock) {
			return fromBush(blockState, itemStack, TheriumCrystalBlock.AGE, 2);
		}
		if (block instanceof CrimsonBerryBushBlock) {
			return fromBush(blockState, itemStack, CrimsonBerryBushBlock.AGE, 1);
		}
		if (block instanceof BaobabFruitBlock) {
			return fromBush(blockState, itemStack, BaobabFruitBlock.AGE, 2);
		}
		if (block instanceof BlueBerryBush) {
			return fromBush(blockState, itemStack, BlueBerryBush.AGE, 1);
		}
		if (block instanceof EtherBulbsBlock) {
			return fromBush(blockState, itemStack, EtherBulbsBlock.AGE, 2);
		}
		if (block instanceof WhitePuffballBlock) {
			return fromBush(blockState, itemStack, WhitePuffballBlock.AGE, 2);
		}
		if (block instanceof AbstractBarrelCactusBlock) {
			if (itemStack.isOf(Items.SHEARS)) {
				return Crosshair.USABLE;
			}
		}
		if (block instanceof BloomingAloeVeraBlock) {
			BlockState blockStateBelow = context.world.getBlockState(context.getBlockPos().down());
			if (itemStack.isOf(Items.SHEARS) && (blockStateBelow.isIn(BlockTags.DIRT) || blockStateBelow.isIn(BlockTags.SAND))) {
				return Crosshair.USABLE;
			}
		}
		if (block instanceof FirecrackerLeavesBlock || block instanceof HydrangeaHedgeBlock) {
			BlockState blockStateBelow = context.world.getBlockState(context.getBlockPos().down());
			if (itemStack.isOf(Items.SHEARS) && blockStateBelow.isIn(BlockTags.DIRT)) {
				return Crosshair.USABLE;
			}
		}
		if (block instanceof CarvedBarrelCactusBlock) {
			if (itemStack.isOf(Items.WATER_BUCKET) || itemStack.isOf(Items.HONEY_BOTTLE)) {
				return Crosshair.USABLE;
			}
		}
		if (block instanceof HoneyBarrelCactusBlock) {
			if (itemStack.isOf(Items.GLASS_BOTTLE)) {
				return Crosshair.USABLE;
			}
		}
		if (block instanceof WaterBarrelCactusBlock) {
			if (itemStack.isOf(Items.BUCKET)) {
				return Crosshair.USABLE;
			}
		}
		return null;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof BiomepediaItem;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof CampfireExplodingBlockItem;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof BYGLilyItem || item instanceof BYGWaterSilkItem) {
			BlockHitResult fluidHitResult = context.raycastWithFluid(RaycastContext.FluidHandling.SOURCE_ONLY);
			if (fluidHitResult.getType() == HitResult.Type.BLOCK) {
				BlockPos fluidPos = fluidHitResult.getBlockPos();
				BlockState blockstate = context.world.getBlockState(fluidPos);
				Material material = blockstate.getMaterial();
				FluidState FluidState = context.world.getFluidState(fluidPos);
				if ((FluidState.getFluid() == Fluids.WATER || material == Material.ICE) && context.world.isAir(fluidPos.up())) {
					return Crosshair.HOLDING_BLOCK;
				}
			}
		}

		if (context.isWithBlock()) {
			if (item instanceof CampfireExplodingBlockItem && context.getBlock() instanceof CampfireBlock) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
