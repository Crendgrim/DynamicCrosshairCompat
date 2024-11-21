//? if techreborn {
package mod.crend.dynamiccrosshair.compat.mixin.techreborn;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import techreborn.blockentity.storage.fluid.TankUnitBaseBlockEntity;
import techreborn.blockentity.storage.item.StorageUnitBaseBlockEntity;
import techreborn.items.UpgraderItem;

@Mixin(value = UpgraderItem.class, remap = false)
public class UpgraderItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		BlockEntity blockEntity = context.getBlockEntity();
		if (blockEntity instanceof StorageUnitBaseBlockEntity || blockEntity instanceof TankUnitBaseBlockEntity) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
