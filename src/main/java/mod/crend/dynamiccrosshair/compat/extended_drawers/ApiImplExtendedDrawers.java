package mod.crend.dynamiccrosshair.compat.extended_drawers;

import io.github.mattidragon.extendeddrawers.block.AccessPointBlock;
import io.github.mattidragon.extendeddrawers.block.ShadowDrawerBlock;
import io.github.mattidragon.extendeddrawers.block.base.StorageDrawerBlock;
import io.github.mattidragon.extendeddrawers.block.entity.ShadowDrawerBlockEntity;
import io.github.mattidragon.extendeddrawers.block.entity.StorageDrawerBlockEntity;
import io.github.mattidragon.extendeddrawers.item.LimiterItem;
import io.github.mattidragon.extendeddrawers.item.UpgradeItem;
import io.github.mattidragon.extendeddrawers.misc.DrawerRaycastUtil;
import io.github.mattidragon.extendeddrawers.registry.ModItems;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec2f;

public class ApiImplExtendedDrawers implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "extended_drawers";
	}

	@Override
	public boolean forceInvalidate(CrosshairContext context) {
		return (context.isWithBlock() && context.getBlock() instanceof StorageDrawerBlock<?>);
	}

	private <T extends StorageDrawerBlockEntity> int getClickedSlot(StorageDrawerBlock<T> drawerBlock, CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Vec2f clickedPosition = DrawerRaycastUtil.calculateFaceLocation(
				context.getBlockPos(),
				context.hitResult.getPos(),
				context.getBlockHitSide(),
				blockState.get(StorageDrawerBlock.FACING),
				blockState.get(StorageDrawerBlock.FACE)
		);
		@SuppressWarnings("unchecked")
		T drawer = (T) context.getBlockEntity();
		return drawerBlock.getSlotIndex(drawer, clickedPosition);
	}
	@SuppressWarnings({"UnstableApiUsage", "unchecked"})
	private <T extends StorageDrawerBlockEntity> StorageView<ItemVariant> getClickedSlotContents(StorageDrawerBlock<T> drawerBlock, CrosshairContext context) {
		return drawerBlock.getSlot((T) context.getBlockEntity(), getClickedSlot(drawerBlock, context));
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof UpgradeItem || item instanceof LimiterItem || item == ModItems.LOCK;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		Item item = context.getItem();

		if (context.isWithBlock() && context.player.isSneaking()) {
			if (item instanceof UpgradeItem || item instanceof LimiterItem || item == ModItems.LOCK) {
				BlockState blockState = context.getBlockState();
				Block block = blockState.getBlock();

				if (block instanceof StorageDrawerBlock<? extends StorageDrawerBlockEntity> drawerBlock && drawerBlock.isFront(blockState, context.getBlockHitSide()) && context.player.canModifyBlocks()) {
					return Crosshair.USABLE;
				}
			}
		}

		return null;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return (   block instanceof StorageDrawerBlock
				|| block instanceof AccessPointBlock
				|| block instanceof ShadowDrawerBlock
		);
	}

	@Override
	@SuppressWarnings("UnstableApiUsage")
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		ItemStack handItemStack = context.getItemStack();
		Block block = blockState.getBlock();

		if (context.isOffHand()) return null;

		if (block instanceof StorageDrawerBlock<? extends StorageDrawerBlockEntity> drawerBlock && drawerBlock.isFront(blockState, context.getBlockHitSide()) && context.player.canModifyBlocks()) {
			if (context.getBlockEntity() instanceof StorageDrawerBlockEntity) {
				StorageView<ItemVariant> storage = getClickedSlotContents(drawerBlock, context);
				ItemVariant storedItem = storage.getResource();

				if (!context.player.isSneaking()) {
					if (handItemStack.isEmpty()) {
						if (!storage.isResourceBlank()) {
							return Crosshair.INTERACTABLE;
						}
					} else {
						if (storedItem.isBlank() || storedItem.getItem() == handItemStack.getItem()) {
							return Crosshair.USABLE;
						}
					}
				}
			}
		}

		if (block instanceof AccessPointBlock) {
			if (handItemStack.isEmpty()) {
				return Crosshair.INTERACTABLE;
			} else {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof ShadowDrawerBlock drawerBlock && context.isMainHand() && drawerBlock.isFront(blockState, context.getBlockHitSide())) {
			ShadowDrawerBlockEntity drawerBlockEntity = (ShadowDrawerBlockEntity) context.getBlockEntity();

			if (context.player.isSneaking()) {
				if (handItemStack.isEmpty()) {
					return Crosshair.INTERACTABLE;
				}
			} else {
				if (handItemStack.isEmpty()) {
					if (!drawerBlockEntity.item.isBlank()) {
						return Crosshair.INTERACTABLE;
					}
				} else {
					if (drawerBlockEntity.item.isBlank() || drawerBlockEntity.item.getItem() == handItemStack.getItem()) {
						return Crosshair.USABLE;
					}
				}
			}
		}
		return null;
	}
}
