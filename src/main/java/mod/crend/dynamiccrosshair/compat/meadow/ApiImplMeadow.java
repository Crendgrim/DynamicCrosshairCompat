package mod.crend.dynamiccrosshair.compat.meadow;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.mixin.meadow.WoodenBucketAccessor;
import mod.crend.dynamiccrosshair.compat.mixin.meadow.WoodenFlowerPotBlockAccessor;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.mixin.IItemMixin;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.satisfyu.meadow.Meadow;
import net.satisfyu.meadow.block.ModBlocks;
import net.satisfyu.meadow.block.cheeseForm.CheeseFormBlock;
import net.satisfyu.meadow.block.cheeseRack.CheeseRackBlock;
import net.satisfyu.meadow.block.cheeseRack.CheeseRackBlockEntity;
import net.satisfyu.meadow.block.cookingCauldron.CookingCauldronBlock;
import net.satisfyu.meadow.block.cookingPot.CookingPotBlock;
import net.satisfyu.meadow.block.custom.*;
import net.satisfyu.meadow.block.flowerPot.FlowerBoxBlock;
import net.satisfyu.meadow.block.flowerPot.FlowerBoxBlockEntity;
import net.satisfyu.meadow.block.flowerPot.ModFlowerPotBlock;
import net.satisfyu.meadow.block.flowerPot.ModFlowerPotBlockEntity;
import net.satisfyu.meadow.block.fondueBlock.FondueBlock;
import net.satisfyu.meadow.block.shelfBlock.ShelfBlock;
import net.satisfyu.meadow.block.windowShutter.ShutterBlock;
import net.satisfyu.meadow.block.woodCutter.WoodcutterBlock;
import net.satisfyu.meadow.entity.custom.buffalo.water_buffalo.WaterBuffaloEntity;
import net.satisfyu.meadow.item.ModItems;
import net.satisfyu.meadow.item.custom.WateringCanItem;
import net.satisfyu.meadow.item.custom.WoodenBucket;
import net.satisfyu.meadow.util.GeneralUtil;
import net.satisfyu.meadow.util.Tags;

import java.util.Optional;

public class ApiImplMeadow implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Meadow.MOD_ID;
	}

	@Override
	public boolean forceInvalidate(CrosshairContext context) {
		if (context.isWithBlock() && context.getBlock() instanceof CheeseRackBlock) {
			return true;
		}
		return (context.getItem() instanceof WoodenBucket);
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof CheeseFormBlock
				|| block instanceof CookingCauldronBlock
				|| block instanceof CookingPotBlock
				|| block instanceof CameraBlock
				|| block instanceof CheeseBlock
				|| block instanceof WarpedCheeseBlock
				|| block instanceof FondueBlock
				|| block instanceof ShelfBlock
				|| (block instanceof ShutterBlock && blockState.getMaterial() != Material.METAL)
				|| block instanceof WoodcutterBlock
				;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof CheeseRackBlock
				|| block instanceof BenchBlock
				|| (block instanceof BowlBlock && block != ModBlocks.BOWL_EMPTY)
				|| block instanceof CanBlock
				|| block instanceof ChairBlock
				|| block instanceof FireLog
				|| block instanceof FrameBlock
				|| block instanceof StoveBlockWood
				|| block instanceof TiledBench
				|| block instanceof WoodenFlowerPotBlock
				|| block instanceof FlowerBoxBlock
				|| block instanceof ModFlowerPotBlock
				;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack itemStack = context.getItemStack();

		if (block instanceof CheeseRackBlock && !context.player.isSneaking() && context.getBlockEntity() instanceof CheeseRackBlockEntity be) {
			Optional<Pair<Float, Float>> optional = GeneralUtil.getRelativeHitCoordinatesForBlockFace(context.getBlockHitResult(), blockState.get(CheeseRackBlock.FACING), null);
			if (optional.isPresent()) {
				int slot = optional.get().getRight() > 0.5 ? 1 : 0;

				if (itemStack.isEmpty() && be.hasStack(slot)) {
					return Crosshair.INTERACTABLE;
				} else if (itemStack.isIn(Tags.CHEESE_BLOCKS) && !be.hasStack(slot)) {
					return Crosshair.USABLE;
				}
			}
		}

		if (block instanceof BenchBlock || block instanceof ChairBlock || block instanceof TiledBench) {
			if (!context.player.isSneaking() && context.getBlockHitSide() != Direction.DOWN) {
				return Crosshair.INTERACTABLE;
			}
		}

		if (block instanceof BowlBlock) {
			if (context.player.getAbilities().allowModifyWorld && itemStack.isEmpty() && !block.equals(ModBlocks.BOWL_EMPTY) && context.player.canConsume(false)) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof CanBlock) {
			Item item = itemStack.getItem();
			if (item instanceof BucketItem || item instanceof MilkBucketItem) {
				if (blockState.get(CanBlock.FLUID) == 0 && (
						   item.equals(Items.MILK_BUCKET)
						|| item.equals(Items.WATER_BUCKET)
					    || item.equals(ModItems.WOODEN_MILK_BUCKET)
					    || item.equals(ModItems.WOODEN_WATER_BUCKET)
				)) {
					return Crosshair.USABLE;
				}

				if ((blockState.get(CanBlock.FLUID) == 1 || blockState.get(CanBlock.FLUID) == 2) && (item.equals(Items.BUCKET) || item.equals(ModItems.WOODEN_BUCKET))) {
					return Crosshair.USABLE;
				}
			}
		}

		if (block instanceof FireLog) {
			int stage = blockState.get(FireLog.STAGE);
			if (itemStack.isOf(block.asItem())) {
				if (stage < 3 && stage > 0) {
					return Crosshair.USABLE;
				}
			} else if (itemStack.isOf(Items.IRON_AXE) && stage == 1 && itemStack.getDamage() == 0) {
				return Crosshair.USABLE;
			} else if (context.player.isSneaking() && itemStack.isEmpty()) {
				return Crosshair.INTERACTABLE;
			}
		}

		if (block instanceof FrameBlock) {
			if (itemStack.isOf(ModBlocks.COOKING_CAULDRON.asItem())) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof StoveBlockWood) {
			if (blockState.get(StoveBlockWood.LIT)) {
				if (itemStack.isIn(ConventionalItemTags.SHOVELS)) {
					return Crosshair.USABLE;
				}
			} else {
				if (itemStack.getItem() instanceof FlintAndSteelItem || itemStack.getItem() instanceof FireChargeItem) {
					return Crosshair.USABLE;
				}
			}
		}

		if (block instanceof WoodenFlowerPotBlock flowerPot) {
			if (itemStack.getItem() instanceof BlockItem blockItem && WoodenFlowerPotBlockAccessor.getWOODEN_CONTENT_TO_POTTED().containsKey(blockItem)) {
				if (((WoodenFlowerPotBlockAccessor) flowerPot).invokeIsEmpty()) {
					return Crosshair.USABLE;
				}
			} else {
				if (!((WoodenFlowerPotBlockAccessor) flowerPot).invokeIsEmpty()) {
					return Crosshair.INTERACTABLE;
				}
			}
		}

		if (block instanceof FlowerBoxBlock
				&& !context.player.isSneaking()
				&& context.getBlockEntity() instanceof FlowerBoxBlockEntity flowerBox
		) {
			if (itemStack.isEmpty()) {
				if (!flowerBox.isSlotEmpty(0) || !flowerBox.isSlotEmpty(1)) {
					return Crosshair.INTERACTABLE;
				}
			} else if (itemStack.isIn(Tags.SMALL_FLOWER)) {
				if (flowerBox.isSlotEmpty(0) || flowerBox.isSlotEmpty(1)) {
					return Crosshair.USABLE;
				}
			}
		}

		if (block instanceof ModFlowerPotBlock flowerPot && context.getBlockEntity() instanceof ModFlowerPotBlockEntity flowerPotEntity) {
			Item flower = flowerPotEntity.getFlower();
			if (itemStack.isEmpty()) {
				if (flower != null) {
					return Crosshair.INTERACTABLE;
				}
			} else if (flowerPot.fitInPot(itemStack) && flower == null) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();
		ItemStack itemStack = context.getItemStack();

		if (entity instanceof CowEntity cow) {
			if (itemStack.isOf(ModItems.WOODEN_BUCKET) && !cow.isBaby()) {
				return Crosshair.USABLE;
			}
		}
		if (entity instanceof WaterBuffaloEntity buffalo) {
			if ((itemStack.isOf(Items.BUCKET) || itemStack.isOf(ModItems.WOODEN_BUCKET)) && !buffalo.isBaby()) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof WateringCanItem || item instanceof WoodenBucket;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof WateringCanItem && context.isWithBlock() && !context.player.isSneaking()) {
			BlockHitResult hitResult = IItemMixin.invokeRaycast(context.world, context.player, RaycastContext.FluidHandling.SOURCE_ONLY);
			BlockPos blockPos;
			if (hitResult.getType() == HitResult.Type.BLOCK) {
				blockPos = hitResult.getBlockPos();
				if (context.world.canPlayerModifyAt(context.player, blockPos)) {
					if (context.world.getFluidState(blockPos).isIn(FluidTags.WATER)) {
						return Crosshair.USABLE;
					}
				}
			}

			if (itemStack.getDamage() < itemStack.getMaxDamage() || context.player.getAbilities().creativeMode) {
				BlockState blockState = context.getBlockState();
				blockPos = context.getBlockPos();
				if (blockState instanceof Fertilizable fertilizable && fertilizable.isFertilizable(context.world, blockPos, blockState, true)) {
					return Crosshair.USABLE;
				} else {
					if (blockState.isSideSolidFullSquare(context.world, blockPos, context.getBlockHitSide())
							&& blockState.isOf(Blocks.WATER) && context.getFluidState().getLevel() == 8) {
						return Crosshair.USABLE;
					}
				}
			}
		}

		if (item instanceof WoodenBucket bucket) {
			Fluid fluid = ((WoodenBucketAccessor) bucket).getFluid();
			BlockHitResult blockHitResult = IItemMixin.invokeRaycast(
					context.world,
					context.player,
					fluid == Fluids.EMPTY ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);
			if (blockHitResult.getType() == HitResult.Type.BLOCK) {
				BlockPos blockPos = blockHitResult.getBlockPos();
				Direction direction = blockHitResult.getSide();
				BlockPos blockPos2 = blockPos.offset(direction);
				if (context.world.canPlayerModifyAt(context.player, blockPos) && context.player.canPlaceOn(blockPos2, direction, itemStack)) {
					BlockState blockState = context.world.getBlockState(blockPos);
					if (fluid == Fluids.EMPTY) {
						if (blockState.getBlock() instanceof FluidDrainable) {
							if (!blockState.getBlock().equals(Blocks.LAVA)) {
								return Crosshair.USABLE;
							}
						}
					} else {
						if (fluid instanceof FlowableFluid) {
							return Crosshair.HOLDING_BLOCK;
						}
					}
				}
			}
		}

		return null;
	}
}
