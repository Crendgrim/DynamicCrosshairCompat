package mod.crend.dynamiccrosshair.compat.vinery;

import de.cristelknight.doapi.common.block.StorageBlock;
import de.cristelknight.doapi.common.block.entity.StorageBlockEntity;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.ItemCategory;
import mod.crend.dynamiccrosshair.compat.mixin.vinery.GrapevinePotBlockAccessor;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.*;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.block.*;
import satisfyu.vinery.block.entity.FlowerBoxBlockEntity;
import satisfyu.vinery.block.entity.FlowerPotBlockEntity;
import satisfyu.vinery.block.grape.GrapeBush;
import satisfyu.vinery.block.grape.GrapeVineBlock;
import satisfyu.vinery.block.stem.LatticeStemBlock;
import satisfyu.vinery.block.stem.PaleStemBlock;
import satisfyu.vinery.block.stem.StemBlock;
import satisfyu.vinery.block.storage.FlowerBoxBlock;
import satisfyu.vinery.block.storage.WineBottleBlock;
import satisfyu.vinery.block.storage.WineBox;
import satisfyu.vinery.item.GrapeBushSeedItem;
import satisfyu.vinery.item.RottenCherryItem;
import satisfyu.vinery.registry.ObjectRegistry;
import satisfyu.vinery.util.VineryTags;
import satisfyu.vinery.util.VineryUtils;

import java.util.Optional;

public class ApiImplVinery implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Vinery.MODID;
	}

	public Crosshair fromBush(BlockState blockState, ItemStack itemStack, IntProperty ageProperty, int maxAge) {
		int age = blockState.get(ageProperty);
		if (age != maxAge && itemStack.isOf(Items.BONE_MEAL)) {
			return Crosshair.USABLE;
		} else if (age > 1) {
			return Crosshair.INTERACTABLE;
		}
		return null;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof FermentationBarrelBlock
				|| block instanceof ApplePressBlock
				|| block instanceof WineRackStorageBlock
		;
	}

	@Override
	public ItemCategory getItemCategory(ItemStack itemStack) {
		if (itemStack.getItem() instanceof RottenCherryItem) {
			return ItemCategory.THROWABLE;
		}
		return DynamicCrosshairApi.super.getItemCategory(itemStack);
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof GrapeBush
				|| block instanceof GrapeVineBlock
				|| block instanceof StemBlock
				|| block instanceof BasketBlock
				|| block instanceof ChairBlock
				|| block instanceof FlowerPotBlock
				|| block instanceof GrapevinePotBlock
				|| block instanceof KitchenSinkBlock
				|| block instanceof StackableLogBlock
				|| block instanceof StorageBlock
		;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack itemStack = context.getItemStack();

		if (block instanceof GrapeBush) {
			return fromBush(blockState, itemStack, GrapeBush.AGE, 3);
		}
		if (block instanceof GrapeVineBlock && context.isMainHand()) {
			if (itemStack.isOf(Items.SHEARS)) {
				return Crosshair.USABLE;
			} else {
				return fromBush(blockState, itemStack, GrapeVineBlock.AGE, 3);
			}
		}

		if (block instanceof StemBlock && context.isMainHand()) {
			int age = blockState.get(StemBlock.AGE);
			if (age > 0 && itemStack.isOf(Items.SHEARS)) {
				return Crosshair.USABLE;
			} else if (age == 0 && itemStack.getItem() instanceof GrapeBushSeedItem seed) {
				if (block instanceof LatticeStemBlock) {
					if (!seed.getType().isPaleType()) {
						return Crosshair.USABLE;
					}
				} else if (block instanceof PaleStemBlock stem) {
					if (stem.hasTrunk(context.world, context.getBlockPos()) && seed.getType().isPaleType()) {
						return Crosshair.USABLE;
					}
				}
			}
			if (age > 3) {
				return Crosshair.INTERACTABLE;
			}
		}

		if (block instanceof BasketBlock) {
			if (context.player.isSneaking() && blockState.get(BasketBlock.STAGE) > 0) {
				return Crosshair.INTERACTABLE;
			} else if (itemStack.isOf(ObjectRegistry.RED_GRAPE.get().asItem()) && blockState.get(BasketBlock.STAGE) < 1) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof ChairBlock) {
			if (!context.player.isSneaking() && context.getBlockHitSide() != Direction.DOWN) {
				return Crosshair.INTERACTABLE;
			}
		}

		if (block instanceof CherryLeaves) {
			if (blockState.get(CherryLeaves.VARIANT) && blockState.get(CherryLeaves.HAS_CHERRIES) && itemStack.getItem() instanceof ShearsItem) {
				return Crosshair.USABLE;
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
			} else if (itemStack.isIn(VineryTags.SMALL_FLOWER)) {
				if (flowerBox.isSlotEmpty(0) || flowerBox.isSlotEmpty(1)) {
					return Crosshair.USABLE;
				}
			}
		}

		if (block instanceof FlowerPotBlock flowerPotBlock
				&& context.isMainHand()
				&& !context.player.isSneaking()
				&& context.getBlockEntity() instanceof FlowerPotBlockEntity flowerPotBlockEntity
		) {
			Item flower = flowerPotBlockEntity.getFlower();
			if (itemStack.isEmpty()) {
				if (flower != null) {
					return Crosshair.INTERACTABLE;
				}
			} else if (flowerPotBlock.fitInPot(itemStack) && flower == null) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof GrapevinePotBlock potBlock) {
			if (itemStack.getItem() instanceof GrapeItem) {
				if (blockState.get(GrapevinePotBlockAccessor.getSTAGE()) <= 3 && blockState.get(GrapevinePotBlockAccessor.getSTORAGE()) < 9) {
					return Crosshair.USABLE;
				}
			} else if (itemStack.isOf(ObjectRegistry.WINE_BOTTLE.get().asItem()) && ((GrapevinePotBlockAccessor) potBlock).invokeCanTakeWine(blockState, itemStack)) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof KitchenSinkBlock) {
			if (itemStack.isEmpty() && !blockState.get(KitchenSinkBlock.FILLED)) {
				return Crosshair.INTERACTABLE;
			} else if ((itemStack.isOf(Items.WATER_BUCKET) || itemStack.isOf(Items.GLASS_BOTTLE)) && !blockState.get(KitchenSinkBlock.FILLED)) {
				return Crosshair.USABLE;
			} else if ((itemStack.isOf(Items.BUCKET) || itemStack.isOf(Items.GLASS_BOTTLE)) && blockState.get(KitchenSinkBlock.FILLED)) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof StackableLogBlock) {
			SlabType stackSize = blockState.get(StackableLogBlock.TYPE);
			if (itemStack.isOf(Items.FLINT_AND_STEEL) && stackSize == SlabType.DOUBLE) {
				return Crosshair.USABLE;
			} else if (itemStack.getItem() instanceof ShovelItem && stackSize == SlabType.DOUBLE && blockState.get(StackableLogBlock.FIRED)) {
				return Crosshair.USABLE;
			}
		}
		if (block instanceof StorageBlock storageBlock && context.getBlockEntity() instanceof StorageBlockEntity shelfBlockEntity) {
			if (block instanceof WineBox) {
				if (context.player.isSneaking() && itemStack.isEmpty()) {
					return Crosshair.INTERACTABLE;
				}
				else if (!blockState.get(WineBox.OPEN)) {
					return null;
				}
			}

			Optional<Pair<Float, Float>> optional = VineryUtils.getRelativeHitCoordinatesForBlockFace(context.getBlockHitResult(), blockState.get(StorageBlock.FACING), storageBlock.unAllowedDirections());
			if (optional.isPresent()) {
				Pair<Float, Float> ff = optional.get();
				int i = storageBlock.getSection(ff.getLeft(), ff.getRight());
				if (i != Integer.MIN_VALUE) {
					if (!(shelfBlockEntity.getInventory().get(i)).isEmpty()) {
						return Crosshair.INTERACTABLE;
					} else if (!itemStack.isEmpty() && storageBlock.canInsertStack(itemStack)) {
						return Crosshair.USABLE;
					}
				}
			}
		}

		if (block instanceof WineBottleBlock wineBottleBlock && context.getBlockEntity() instanceof StorageBlockEntity wineEntity) {
			DefaultedList<ItemStack> inventory = wineEntity.getInventory();
			if (wineBottleBlock.canInsertStack(itemStack) && wineBottleBlock.willFitStack(itemStack, inventory)) {
				if (wineBottleBlock.getFirstEmptySlot(inventory) != Integer.MIN_VALUE) {
					return Crosshair.USABLE;
				}
			}
			else if (itemStack.isEmpty() && !wineBottleBlock.isEmpty(inventory)) {
				if (wineBottleBlock.getLastFullSlot(inventory) != Integer.MIN_VALUE) {
					return Crosshair.INTERACTABLE;
				}
			}
		}

		return null;
	}
}
