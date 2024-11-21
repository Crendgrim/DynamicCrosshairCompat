package mod.crend.dynamiccrosshair.compat.helper;

import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
//? if bclib {
import org.betterx.bclib.blocks.BaseStripableBarkBlock;
import org.betterx.bclib.blocks.BaseStripableLogBlock;
//?}

public class BCLibHandler {
	public static Crosshair checkUsableItem(CrosshairContext context) {
		//? if bclib {
		if (context.isWithBlock()) {
			Item handItem = context.getItem();
			Block block = context.getBlock();
			if (handItem instanceof AxeItem) {
				if (block instanceof BaseStripableLogBlock || block instanceof BaseStripableBarkBlock) {
					return new Crosshair(InteractionType.CORRECT_TOOL, InteractionType.USE_ITEM_ON_BLOCK);
				}
			}
		}
		//?}
		return null;
	}
}
