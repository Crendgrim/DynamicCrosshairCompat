package mod.crend.dynamiccrosshair.compat.betteranimalsplus;

import dev.itsmeow.betteranimalsplus.common.block.BlockTurkey;
import dev.itsmeow.betteranimalsplus.common.item.ItemThrowableCustomEgg;
import dev.itsmeow.betteranimalsplus.imdlib.entity.interfaces.IBucketable;
import dev.itsmeow.betteranimalsplus.imdlib.item.ItemModFishBucket;
import mod.crend.dynamiccrosshair.DynamicCrosshair;
import mod.crend.dynamiccrosshair.api.*;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.component.CrosshairVariant;
import mod.crend.dynamiccrosshair.component.ModifierUse;
import mod.crend.dynamiccrosshair.config.BlockCrosshairPolicy;
import net.minecraft.item.ItemStack;

public class ApiImplBetterAnimalsPlus implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "betteranimalsplus";
	}

	@Override
	public Crosshair checkEntity(CrosshairContext context) {
		if (context.getEntity() instanceof IBucketable) {
			return Crosshair.USE_ITEM;
		}

		return null;
	}

	@Override
	public Crosshair checkThrowable(CrosshairContext context) {
		if (context.getItem() instanceof ItemThrowableCustomEgg<?>) {
			return Crosshair.THROWABLE;
		}

		return null;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		if (context.getBlock() instanceof BlockTurkey) {
			if (context.player.canConsume(false)) {
				return Crosshair.USE_ITEM;
			}
		}

		return null;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof ItemModFishBucket<?>;
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		if (context.isWithBlock()) {
			if (context.getItem() instanceof ItemModFishBucket<?>) {
				if (DynamicCrosshair.config.dynamicCrosshairHoldingBlock() != BlockCrosshairPolicy.Disabled) {
					return new Crosshair(CrosshairVariant.HoldingBlock, ModifierUse.USE_ITEM);
				}
				return Crosshair.USE_ITEM;
			}
		} else if (!context.isTargeting()) {
			if (DynamicCrosshair.config.dynamicCrosshairHoldingBlock() == BlockCrosshairPolicy.Always) {
				if (context.getItem() instanceof ItemModFishBucket<?>) {
					return new Crosshair(CrosshairVariant.HoldingBlock, ModifierUse.USE_ITEM);
				}
			}
		}
		return null;
	}
}
