package mod.crend.dynamiccrosshair.compat.edenring;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
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
	public Crosshair checkBlockItem(CrosshairContext context) {
		if (context.getItem() instanceof EdenPaintingItem) {
			Direction side = context.getBlockHitSide();
			if (!side.getAxis().isVertical() && context.player.canPlaceOn(context.getBlockPos(), side, context.getItemStack())) {
				return Crosshair.HOLDING_BLOCK;
			}
		}

		return null;
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		if (context.getItem() instanceof BalloonMushroomBlockItem) {
			return Crosshair.HOLDING_BLOCK;
		}
		return BCLibUsableItemHandler.checkUsableItem(context);
	}

}
