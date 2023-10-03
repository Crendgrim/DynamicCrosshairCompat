package mod.crend.dynamiccrosshair.compat.meadow;

import juuxel.adorn.block.ChairBlock;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.mixin.meadow.WoodenBucketAccessor;
import mod.crend.dynamiccrosshair.compat.mixin.meadow.WoodenFlowerPotBlockAccessor;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.mixin.ItemAccessor;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.satisfyu.meadow.Meadow;
import net.satisfyu.meadow.block.FlowerPotBlock;
import net.satisfyu.meadow.block.*;
import net.satisfyu.meadow.entity.blockentities.CheeseRackBlockEntity;
import net.satisfyu.meadow.entity.blockentities.FlowerBoxBlockEntity;
import net.satisfyu.meadow.entity.blockentities.FlowerPotBlockEntity;
import net.satisfyu.meadow.entity.buffalo.WaterBuffalo;
import net.satisfyu.meadow.item.WateringCanItem;
import net.satisfyu.meadow.item.WoodenBucket;
import net.satisfyu.meadow.registry.ObjectRegistry;
import net.satisfyu.meadow.registry.TagRegistry;
import net.satisfyu.meadow.util.GeneralUtil;

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
				|| block instanceof CameraBlock
				|| block instanceof CheeseBlock
				|| block instanceof FondueBlock
				|| block instanceof ShelfBlock
				|| block instanceof ShutterBlock
				|| block instanceof WoodcutterBlock
				;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof CheeseRackBlock
				|| block instanceof BenchBlock
				|| block instanceof CanBlock
				|| block instanceof ChairBlock
				|| block instanceof FireLog
				|| block instanceof FlowerPotBlock
				|| block instanceof FrameBlock
				|| block instanceof StoveBlockWood
				|| block instanceof TiledBench
				|| block instanceof WoodenFlowerPotBlock
				|| block instanceof FlowerBoxBlock
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
				} else if (itemStack.isIn(TagRegistry.CHEESE_BLOCKS) && !be.hasStack(slot)) {
					return Crosshair.USABLE;
				}
			}
		}

		if (block instanceof BenchBlock || block instanceof ChairBlock || block instanceof TiledBench) {
			if (!context.player.isSneaking() && context.getBlockHitSide() != Direction.DOWN) {
				return Crosshair.INTERACTABLE;
			}
		}

		if (block instanceof CanBlock) {
			Item item = itemStack.getItem();
			if (item instanceof BucketItem || item instanceof MilkBucketItem) {
				if (blockState.get(CanBlock.FLUID) == 0 && (
						   item.equals(Items.MILK_BUCKET)
						|| item.equals(Items.WATER_BUCKET)
					    || item.equals(ObjectRegistry.WOODEN_MILK_BUCKET.get())
					    || item.equals(ObjectRegistry.WOODEN_WATER_BUCKET.get())
				)) {
					return Crosshair.USABLE;
				}

				if ((blockState.get(CanBlock.FLUID) == 1 || blockState.get(CanBlock.FLUID) == 2) && (item.equals(Items.BUCKET) || item.equals(ObjectRegistry.WOODEN_BUCKET.get()))) {
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
			if (itemStack.isOf(ObjectRegistry.COOKING_CAULDRON.get().asItem())) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof StoveBlockWood) {
			if (blockState.get(StoveBlockWood.LIT)) {
				if (itemStack.isIn(TagRegistry.SHOVEL)) {
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
			} else if (itemStack.isIn(TagRegistry.SMALL_FLOWER)) {
				if (flowerBox.isSlotEmpty(0) || flowerBox.isSlotEmpty(1)) {
					return Crosshair.USABLE;
				}
			}
		}

		if (block instanceof FlowerPotBlock flowerPot && context.getBlockEntity() instanceof FlowerPotBlockEntity flowerPotEntity) {
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
			if (itemStack.isOf(ObjectRegistry.WOODEN_BUCKET.get()) && !cow.isBaby()) {
				return Crosshair.USABLE;
			}
		}
		if (entity instanceof WaterBuffalo buffalo) {
			if ((itemStack.isOf(Items.BUCKET) || itemStack.isOf(ObjectRegistry.WOODEN_BUCKET.get())) && !buffalo.isBaby()) {
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
			BlockHitResult hitResult = ItemAccessor.invokeRaycast(context.world, context.player, RaycastContext.FluidHandling.SOURCE_ONLY);
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
			BlockHitResult blockHitResult = ItemAccessor.invokeRaycast(
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
