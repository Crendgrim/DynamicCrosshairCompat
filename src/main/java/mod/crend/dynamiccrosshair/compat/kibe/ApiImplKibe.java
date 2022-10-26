package mod.crend.dynamiccrosshair.compat.kibe;

import io.github.lucaargolo.kibe.KibeModKt;
import io.github.lucaargolo.kibe.blocks.bigtorch.BigTorch;
import io.github.lucaargolo.kibe.blocks.breaker.Breaker;
import io.github.lucaargolo.kibe.blocks.chunkloader.ChunkLoader;
import io.github.lucaargolo.kibe.blocks.cooler.Cooler;
import io.github.lucaargolo.kibe.blocks.drawbridge.Drawbridge;
import io.github.lucaargolo.kibe.blocks.entangledchest.EntangledChest;
import io.github.lucaargolo.kibe.blocks.entangledtank.EntangledTank;
import io.github.lucaargolo.kibe.blocks.entangledtank.EntangledTankEntity;
import io.github.lucaargolo.kibe.blocks.miscellaneous.BlockGenerator;
import io.github.lucaargolo.kibe.blocks.miscellaneous.CursedDirt;
import io.github.lucaargolo.kibe.blocks.miscellaneous.FluidHopper;
import io.github.lucaargolo.kibe.blocks.miscellaneous.RedstoneTimer;
import io.github.lucaargolo.kibe.blocks.placer.Placer;
import io.github.lucaargolo.kibe.blocks.tank.Tank;
import io.github.lucaargolo.kibe.blocks.trashcan.TrashCan;
import io.github.lucaargolo.kibe.blocks.vacuum.VacuumHopper;
import io.github.lucaargolo.kibe.blocks.witherbuilder.WitherBuilder;
import io.github.lucaargolo.kibe.items.cooler.CoolerBlockItem;
import io.github.lucaargolo.kibe.items.entangledbag.EntangledBag;
import io.github.lucaargolo.kibe.items.entangledbucket.EntangledBucket;
import io.github.lucaargolo.kibe.items.miscellaneous.*;
import io.github.lucaargolo.kibe.items.trashcan.PocketTrashCan;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;

public class ApiImplKibe implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return KibeModKt.MOD_ID;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();

		if (block instanceof BigTorch
				|| block instanceof Breaker
				|| block instanceof Cooler
				|| block instanceof Drawbridge
				|| block instanceof BlockGenerator
				|| block instanceof RedstoneTimer
				|| block instanceof Placer
				|| block instanceof Tank
				|| block instanceof TrashCan
				|| block instanceof VacuumHopper
				|| block instanceof WitherBuilder
		) {
			return Crosshair.INTERACTABLE;
		}
		if (block instanceof ChunkLoader && blockState.get(Properties.ENABLED)) {
			return Crosshair.INTERACTABLE;
		}
		if (block instanceof EntangledChest || block instanceof EntangledTank) {
			if (context.hitResult.getPos().getY() - context.getBlockPos().getY() > 0.9375) {
				Item item = context.getItem();
				if (item instanceof Rune || item == Items.DIAMOND || item == Items.GOLD_INGOT) {
					return Crosshair.USE_ITEM;
				}
			}
			if (block instanceof EntangledChest) {
				if (EntangledChest.Companion.canOpen(context.world, context.getBlockPos())) {
					return Crosshair.INTERACTABLE;
				}
			} else if (context.getBlockEntity() instanceof EntangledTankEntity blockEntity && context.canInteractWithFluidStorage(blockEntity.getTank())){
				return Crosshair.USE_ITEM;
			}
		}
		if (block instanceof CursedDirt) {
			if (context.isMainHand() && context.player.isSneaking()) {
				return Crosshair.INTERACTABLE;
			}
		}
		if (block instanceof FluidHopper) {
			// TOOD fabric fluid API interactions
			return Crosshair.INTERACTABLE;
		}

		return null;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof EntangledBag
				|| item instanceof Glider
				|| item instanceof PocketCraftingTable
				|| item instanceof PocketTrashCan;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof CoolerBlockItem
				|| item instanceof EntangledBucket
				|| item instanceof BooleanItem
				|| item instanceof CursedSeeds
				|| item instanceof Lasso
				|| item instanceof SleepingBag;
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof CoolerBlockItem) {
			if (!context.isWithBlock() || context.player.isSneaking()) {
				return Crosshair.USE_ITEM;
			}
		}
		if (item instanceof EntangledBucket) {
			if (context.isWithBlock() && context.player.isSneaking() && context.getBlock() instanceof EntangledTank) {
				return Crosshair.USE_ITEM;
			}

			NbtCompound tag = EntangledBucket.Companion.getTag(itemStack);
			StringBuilder colorCode = new StringBuilder();
			for (int i = 1; i <= 8; i++) {
				DyeColor dc = DyeColor.byName(tag.getString("rune" + i), DyeColor.WHITE);
				colorCode.append(Integer.toHexString(dc.getId()));
			}
			tag.putString("colorCode", colorCode.toString());
			SingleVariantStorage<FluidVariant> fluidInv = EntangledBucket.Companion.getFluidInv(context.world, tag);
			Fluid fluid;
			if (fluidInv.isResourceBlank()) {
				fluid = Fluids.EMPTY;
			} else {
				fluid = fluidInv.getResource().getFluid();
			}
			boolean hasSpace = (fluidInv.getAmount() + FluidConstants.BUCKET) <= fluidInv.getCapacity();
			boolean hasBucket = fluidInv.getAmount() >= FluidConstants.BUCKET;

			BlockHitResult hitResult = context.raycastWithFluid(hasSpace ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);
			if (hitResult != null) {
				boolean isBucket = (context.player.isSneaking() || fluid.getBucketItem() == Items.BUCKET);
				if (isBucket && hasSpace) {
					return Crosshair.USE_ITEM;
				}
				Direction dir = hitResult.getSide();
				BlockPos pos = hitResult.getBlockPos();
				BlockPos offsetPos = pos.offset(dir);
				if (context.player.canPlaceOn(offsetPos, dir, itemStack)) {
					BlockState blockState = context.world.getBlockState(pos);
					if (blockState.getBlock() instanceof FluidDrainable) {
						if (hasSpace) {
							return Crosshair.USE_ITEM;
						}
					} else if (hasBucket) {
						BlockPos interactablePos = (blockState.getBlock() instanceof FluidFillable && fluid == Fluids.WATER) ? pos : offsetPos;
						BlockState interactableBlockState = context.world.getBlockState(interactablePos);
						if (!(interactableBlockState.getBlock() instanceof FluidDrainable)
								|| (interactableBlockState.contains(Properties.LEVEL_15) && interactableBlockState.get(Properties.LEVEL_15) != 0)) {
							return Crosshair.HOLDING_BLOCK;
						}
					}
				}
			}
		}
		if (item instanceof BooleanItem) {
			if (context.player.isSneaking()) {
				return Crosshair.USE_ITEM;
			}
		}
		if (item instanceof CursedSeeds && context.isWithBlock()) {
			Block block = context.getBlock();
			if ((block == Blocks.DIRT || block == Blocks.GRASS_BLOCK)
					&& context.world.getLightLevel(context.getBlockPos()) <= 7) {
				return Crosshair.USE_ITEM;
			}
		}
		if (item instanceof Lasso lasso) {
			if (context.isWithEntity()) {
				if (itemStack.hasNbt() && !itemStack.getNbt().contains("Entity")) {
					Entity entity = context.getEntity();
					if (entity instanceof MobEntity && lasso.canStoreEntity(entity.getType())) {
						return Crosshair.USE_ITEM;
					}
				}
			} else if (context.isWithBlock()) {
				if (itemStack.hasNbt() && itemStack.getNbt().contains("Entity")) {
					return Crosshair.USE_ITEM;
				}
			}
		}
		// LightRing?
		if (item instanceof SleepingBag) {
			if (context.isWithBlock() || context.player.isOnGround()) {
				return Crosshair.USE_ITEM;
			}
		}
		if (item instanceof VoidBucket) {
			if (context.isWithBlock()) {
				if (context.getBlock() instanceof FluidDrainable) {
					return Crosshair.USE_ITEM;
				}
			}
			if (!context.isWithEntity()) {
				BlockHitResult fluidHitResult = context.raycastWithFluid(RaycastContext.FluidHandling.SOURCE_ONLY);
				if (fluidHitResult.getType() != HitResult.Type.MISS) {
					BlockState fluidBlockState = context.world.getBlockState(fluidHitResult.getBlockPos());
					if (fluidBlockState.getBlock() instanceof FluidDrainable) {
						return Crosshair.USE_ITEM;
					}
				}
			}
		}
		if (item instanceof WoodenBucket bucket) {
			if (bucket.getFluid() == Fluids.EMPTY) {
				if (context.isWithBlock()) {
					if (context.getBlock() instanceof FluidDrainable) {
						return Crosshair.USE_ITEM;
					}
				}
				if (!context.isWithEntity()) {
					BlockHitResult fluidHitResult = context.raycastWithFluid(RaycastContext.FluidHandling.SOURCE_ONLY);
					if (fluidHitResult.getType() != HitResult.Type.MISS) {
						BlockState fluidBlockState = context.world.getBlockState(fluidHitResult.getBlockPos());
						if (fluidBlockState.getBlock() instanceof FluidDrainable) {
							return Crosshair.USE_ITEM;
						}
					}
				}
			} else if (context.isWithBlock()) {
				if (context.getBlock() instanceof Waterloggable && bucket.getFluid() == Fluids.WATER) {
					return Crosshair.USE_ITEM;
				}
				return Crosshair.HOLDING_BLOCK;
			}
		}

		return null;
	}
}
