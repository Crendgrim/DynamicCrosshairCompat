//? if adorn {
package mod.crend.dynamiccrosshair.compat.mixin.adorn;

import juuxel.adorn.block.KitchenSinkBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = KitchenSinkBlock.class, remap = false)
public class KitchenSinkBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItemStack().isOf(Items.SPONGE) || context.getItem() instanceof BucketItem) {
			return InteractionType.FILL_ITEM_FROM_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
