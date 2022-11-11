package mod.crend.dynamiccrosshair.compat.bclib;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.component.CrosshairVariant;
import mod.crend.dynamiccrosshair.component.ModifierHit;
import mod.crend.dynamiccrosshair.component.ModifierUse;
import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import org.betterx.bclib.blocks.BaseStripableLogBlock;
import org.betterx.bclib.blocks.StripableBarkBlock;

public class BCLibUsableItemHandler {
	public static Crosshair checkUsableItem(CrosshairContext context) {
		if (context.isWithBlock()) {
			Item handItem = context.getItem();
			Block block = context.getBlock();
			if (handItem instanceof AxeItem) {
				if (block instanceof BaseStripableLogBlock || block instanceof StripableBarkBlock) {
					return Crosshair.CORRECT_TOOL.withModifier(ModifierUse.USE_ITEM);
				}
			}
		}
		return null;
	}
}
