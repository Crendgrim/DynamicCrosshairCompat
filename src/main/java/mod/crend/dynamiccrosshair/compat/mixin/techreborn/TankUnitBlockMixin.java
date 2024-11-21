//? if techreborn {
package mod.crend.dynamiccrosshair.compat.mixin.techreborn;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import reborncore.common.fluid.container.ItemFluidInfo;
import techreborn.blocks.storage.fluid.TankUnitBlock;
import techreborn.items.DynamicCellItem;

@Mixin(value = TankUnitBlock.class, remap = false)
public class TankUnitBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		Item item = context.getItem();
		if ((item instanceof DynamicCellItem || item instanceof BucketItem) && item instanceof ItemFluidInfo) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
