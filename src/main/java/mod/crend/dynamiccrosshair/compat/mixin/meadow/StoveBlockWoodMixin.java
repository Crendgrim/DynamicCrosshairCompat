//? if meadow {
package mod.crend.dynamiccrosshair.compat.mixin.meadow;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.satisfy.meadow.core.block.StoveBlockWood;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = StoveBlockWood.class, remap = false)
public class StoveBlockWoodMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (context.getBlockState().get(StoveBlockWood.LIT)) {
			if (itemStack.isIn(ItemTags.SHOVELS)) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		} else {
			if (itemStack.getItem() instanceof FlintAndSteelItem || itemStack.getItem() instanceof FireChargeItem) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
