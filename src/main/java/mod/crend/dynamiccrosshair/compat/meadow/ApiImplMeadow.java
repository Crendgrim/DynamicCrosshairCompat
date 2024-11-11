package mod.crend.dynamiccrosshair.compat.meadow;

import de.cristelknight.doapi.Util;
import de.cristelknight.doapi.common.block.BenchBlock;
import de.cristelknight.doapi.common.block.ChairBlock;
import de.cristelknight.doapi.common.block.FlowerBoxBlock;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.mixin.meadow.WoodenBucketAccessor;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.mixin.ItemAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.FluidDrainable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.satisfy.meadow.Meadow;
import net.satisfy.meadow.block.CameraBlock;
import net.satisfy.meadow.block.CanBlock;
import net.satisfy.meadow.block.CheeseBlock;
import net.satisfy.meadow.block.CheeseFormBlock;
import net.satisfy.meadow.block.CookingCauldronBlock;
import net.satisfy.meadow.block.FireLog;
import net.satisfy.meadow.block.FondueBlock;
import net.satisfy.meadow.block.FrameBlock;
import net.satisfy.meadow.block.ShutterBlock;
import net.satisfy.meadow.block.StoveBlockWood;
import net.satisfy.meadow.block.WoodcutterBlock;
import net.satisfy.meadow.block.entity.CheeseRackBlockEntity;
import net.satisfy.meadow.block.storage.CheeseRackBlock;
import net.satisfy.meadow.entity.WaterBuffalo;
import net.satisfy.meadow.item.WateringCanItem;
import net.satisfy.meadow.item.WoodenBucket;
import net.satisfy.meadow.registry.ObjectRegistry;
import net.satisfy.meadow.registry.TagRegistry;

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
				|| block instanceof FlowerBoxBlock
				;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack itemStack = context.getItemStack();

		if (block instanceof CheeseRackBlock cheeseRackBlock && !context.player.isSneaking() && context.getBlockEntity() instanceof CheeseRackBlockEntity be) {
			Optional<Pair<Float, Float>> optional = Util.getRelativeHitCoordinatesForBlockFace(context.getBlockHitResult(), blockState.get(CheeseRackBlock.FACING), cheeseRackBlock.unAllowedDirections());
			if (optional.isPresent()) {
				int slot = optional.get().getRight() > 0.5 ? 1 : 0;

				if (itemStack.isEmpty() && be.hasStack(slot)) {
					return Crosshair.INTERACTABLE;
				} else if (itemStack.isIn(TagRegistry.CHEESE_BLOCKS) && !be.hasStack(slot)) {
					return Crosshair.USABLE;
				}
			}
		}

		if (block instanceof BenchBlock || block instanceof ChairBlock) {
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
			Fluid fluid = ((WoodenBucketAccessor) bucket).getContent();
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
