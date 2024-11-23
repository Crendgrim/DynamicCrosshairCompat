//? if multibeds {
package mod.crend.dynamiccrosshair.compat.mixin.multibeds;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import shetiphian.multibeds.common.item.ItemBedCustomization;
import shetiphian.multibeds.common.item.ItemBeddingPackage;

//? if =1.20.1 {
import shetiphian.multibeds.common.block.BlockMultiBedBase;
//?} else
/*import shetiphian.multibeds.common.block.BlockMultiBed;*/

@Mixin(value = /*? if =1.20.1 {*/BlockMultiBedBase/*?} else {*//*BlockMultiBed*//*?}*/.class, remap = false)
public class BlockMultiBedBaseMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		Item item = context.getItem();
		if (item instanceof ItemBedCustomization || item instanceof ItemBeddingPackage) {
			return InteractionType.PLACE_ITEM_ON_BLOCK;
		}
		return InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
