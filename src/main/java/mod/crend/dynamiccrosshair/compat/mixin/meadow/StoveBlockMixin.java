//? if meadow {
package mod.crend.dynamiccrosshair.compat.mixin.meadow;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
//? if =1.20.1 {
import net.satisfy.meadow.block.StoveBlock;
import net.satisfy.meadow.block.StoveBlockWood;
import net.satisfy.meadow.registry.TagRegistry;
//?} else {
/*import net.satisfyu.meadow.block.StoveBlock;
import net.satisfyu.meadow.block.StoveBlockWood;
import net.satisfyu.meadow.registry.TagRegistry;
*///?}
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = StoveBlock.class, remap = false)
public class StoveBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (context.getBlockState().get(StoveBlockWood.LIT)) {
			if (itemStack.isIn(TagRegistry.SHOVEL)) {
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
