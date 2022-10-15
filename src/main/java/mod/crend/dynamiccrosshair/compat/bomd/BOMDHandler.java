package mod.crend.dynamiccrosshair.compat.bomd;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.barribob.boss.block.BrimstoneNectarItem;
import net.barribob.boss.block.ChiseledStoneAltarBlock;
import net.barribob.boss.block.ModBlocks;
import net.barribob.boss.item.ChargedEnderPearlItem;
import net.barribob.boss.item.EarthdiveSpear;
import net.barribob.boss.item.SoulStarItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BOMDHandler {

	public static boolean isAlwaysUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof BrimstoneNectarItem;
	}

	public static boolean isUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof SoulStarItem;
	}

	public static Crosshair checkMeleeWeapon(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof EarthdiveSpear) {
			if (context.isActiveItem() && (item.getMaxUseTime(itemStack) - context.player.getItemUseTimeLeft() > 10)) {
				return Crosshair.RANGED_WEAPON;
			}
			return Crosshair.MELEE_WEAPON;
		}

		return null;
	}

	public static Crosshair checkUsableItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof SoulStarItem) {
			if (context.isWithBlock() && context.getBlockState().isOf(ModBlocks.INSTANCE.getChiseledStoneAltar())) {
				if (!context.getBlockState().get(ChiseledStoneAltarBlock.Companion.getLit())) {
					return Crosshair.USE_ITEM;
				}
			} else {
				return Crosshair.USE_ITEM;
			}
		}

		if (item instanceof ChargedEnderPearlItem) {
			return Crosshair.THROWABLE;
		}



		return null;
	}
}
