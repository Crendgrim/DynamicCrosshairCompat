package mod.crend.dynamiccrosshair.compat.bclib;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import ru.bclib.blocks.BaseStripableLogBlock;
import ru.bclib.blocks.StripableBarkBlock;

public class BCLibUsableItemHandler {
	public static Crosshair checkUsableItem(CrosshairContext context) {
		if (context.isWithBlock()) {
			Item handItem = context.getItem();
			Block block = context.getBlock();
			if (handItem instanceof AxeItem) {
				if (block instanceof BaseStripableLogBlock || block instanceof StripableBarkBlock) {
					return Crosshair.USE_ITEM;
				}
			}
		}
		return null;
	}
}
