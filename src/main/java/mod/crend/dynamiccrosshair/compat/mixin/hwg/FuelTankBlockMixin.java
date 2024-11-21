//? if happiness-is-a-warm-gun {
package mod.crend.dynamiccrosshair.compat.mixin.hwg;

import mod.azure.hwg.blocks.FuelTankBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = FuelTankBlock.class, remap = false)
public class FuelTankBlockMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		Item item;
		item = context.getItem();
		if (item == Items.FLINT_AND_STEEL || item == Items.FIRE_CHARGE) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
