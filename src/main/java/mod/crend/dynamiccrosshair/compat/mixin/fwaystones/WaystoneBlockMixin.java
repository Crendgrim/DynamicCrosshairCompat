//? if waystones {
package mod.crend.dynamiccrosshair.compat.mixin.fwaystones;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import wraith.fwaystones.block.WaystoneBlock;
import wraith.fwaystones.item.LocalVoidItem;
import wraith.fwaystones.item.WaystoneDebuggerItem;
import wraith.fwaystones.item.WaystoneScrollItem;

@Mixin(value = WaystoneBlock.class, remap = false)
public class WaystoneBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext crosshairContext) {
		Item item = crosshairContext.getItem();
		if (item == Items.VINE || item == Items.SHEARS) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		if (!(item instanceof WaystoneScrollItem) && !(item instanceof LocalVoidItem) && !(item instanceof WaystoneDebuggerItem)) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
