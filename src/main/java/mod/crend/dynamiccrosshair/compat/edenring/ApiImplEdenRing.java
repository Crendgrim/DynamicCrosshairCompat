package mod.crend.dynamiccrosshair.compat.edenring;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.ItemCategory;
import mod.crend.dynamiccrosshair.compat.bclib.BCLibUsableItemHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import paulevs.edenring.EdenRing;
import paulevs.edenring.items.BalloonMushroomBlockItem;
import paulevs.edenring.items.EdenPaintingItem;
import paulevs.edenring.items.GuideBookItem;

public class ApiImplEdenRing implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return EdenRing.MOD_ID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof GuideBookItem;
	}

	@Override
	public ItemCategory getItemCategory(ItemStack itemStack) {
		if (itemStack.getItem() instanceof EdenPaintingItem || itemStack.getItem() instanceof BalloonMushroomBlockItem) {
			return ItemCategory.BLOCK;
		}
		return DynamicCrosshairApi.super.getItemCategory(itemStack);
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (context.includeHoldingBlock()) {
			if (context.getItem() instanceof EdenPaintingItem) {
				Direction side = context.getBlockHitSide();
				if (!side.getAxis().isVertical() && context.player.canPlaceOn(context.getBlockPos(), side, context.getItemStack())) {
					return Crosshair.HOLDING_BLOCK;
				}
			}

			if (context.getItem() instanceof BalloonMushroomBlockItem) {
				return Crosshair.HOLDING_BLOCK;
			}
		}
		if (context.includeUsableItem()) {
			return BCLibUsableItemHandler.checkUsableItem(context);
		}
		return null;
	}

}
