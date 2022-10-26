package mod.crend.dynamiccrosshair.compat.extended_drawers;

import io.github.mattidragon.extendeddrawers.ExtendedDrawers;
import io.github.mattidragon.extendeddrawers.block.AccessPointBlock;
import io.github.mattidragon.extendeddrawers.block.DrawerBlock;
import io.github.mattidragon.extendeddrawers.block.ShadowDrawerBlock;
import io.github.mattidragon.extendeddrawers.block.entity.DrawerBlockEntity;
import io.github.mattidragon.extendeddrawers.block.entity.ShadowDrawerBlockEntity;
import io.github.mattidragon.extendeddrawers.drawer.DrawerSlot;
import io.github.mattidragon.extendeddrawers.item.LockItem;
import io.github.mattidragon.extendeddrawers.item.UpgradeItem;
import io.github.mattidragon.extendeddrawers.misc.DrawerRaycastUtil;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.mixin.extended_drawers.IDrawerBlockMixin;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec2f;

import static io.github.mattidragon.extendeddrawers.block.DrawerBlock.FACING;

public class ApiImplExtendedDrawers implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return ExtendedDrawers.MOD_ID;
	}

	@Override
	public boolean forceInvalidate(CrosshairContext context) {
		return (context.isWithBlock() && context.getBlock() instanceof DrawerBlock);
	}

	private boolean clickedFace(CrosshairContext context) {
		return (context.getBlockHitSide() == context.getBlockState().get(FACING));
	}

	private DrawerSlot clickedSlot(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		BlockHitResult hit = context.getBlockHitResult();
		Vec2f internalPos = DrawerRaycastUtil.calculateFaceLocation(context.getBlockPos(), hit.getPos(), hit.getSide(), blockState.get(FACING));
		int slot = ((IDrawerBlockMixin) blockState.getBlock()).invokeGetSlot(internalPos);
		DrawerBlockEntity drawer = (DrawerBlockEntity) context.getBlockEntity();
		return drawer.storages[slot];
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof LockItem || item instanceof UpgradeItem;
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		Item item = context.getItem();

		if (context.isWithBlock() && context.player.isSneaking()) {
			Block block = context.getBlock();

			if (block instanceof AccessPointBlock) {
				if (item instanceof LockItem) {
					return Crosshair.USE_ITEM;
				}
			}

			if (block instanceof DrawerBlock && clickedFace(context)) {
				if (item instanceof LockItem || item == Items.LAVA_BUCKET) {
					return Crosshair.USE_ITEM;
				}

				if (item instanceof UpgradeItem && clickedSlot(context).getUpgrade() != item) {
					return Crosshair.USE_ITEM;
				}
			}
		}

		return null;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		ItemStack handItemStack = context.getItemStack();
		Block block = blockState.getBlock();

		if (block instanceof DrawerBlock && context.isMainHand() && clickedFace(context)) {
			if (context.player.isSneaking()) {
				if (handItemStack.isEmpty() && clickedSlot(context).getUpgrade() != null) {
					return Crosshair.INTERACTABLE;
				}
			} else {
				if (handItemStack.isEmpty()) {
					if (!clickedSlot(context).getItem().isBlank()) {
						return Crosshair.INTERACTABLE;
					}
				} else {
					ItemVariant storedItem = clickedSlot(context).getItem();
					if (storedItem.isBlank() || storedItem.getItem() == handItemStack.getItem()) {
						return Crosshair.USE_ITEM;
					}
				}

			}
		}

		if (block instanceof AccessPointBlock) {
			if (handItemStack.isEmpty()) {
				return Crosshair.INTERACTABLE;
			} else {
				return Crosshair.USE_ITEM;
			}
		}

		if (block instanceof ShadowDrawerBlock && context.isMainHand() && clickedFace(context)) {
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
						return Crosshair.USE_ITEM;
					}
				}
			}
		}
		return null;
	}
}
