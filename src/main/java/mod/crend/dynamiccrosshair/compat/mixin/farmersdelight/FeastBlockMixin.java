//? if farmers-delight-refabricated {
package mod.crend.dynamiccrosshair.compat.mixin.farmersdelight;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.IntProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import vectorwing.farmersdelight.common.block.FeastBlock;

@Mixin(value = FeastBlock.class, remap = false)
public abstract class FeastBlockMixin implements DynamicCrosshairBlock {

	@Shadow public abstract IntProperty getServingsProperty();
	@Shadow public abstract ItemStack getServingItem(BlockState state);

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		int servings = context.getBlockState().get(getServingsProperty());
		if (servings == 0) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		ItemStack serving = getServingItem(context.getBlockState());
		ItemStack heldItem = context.getItemStack();
		if (!serving.getRecipeRemainder().isEmpty() || ItemStack.areItemsEqual(heldItem, serving.getRecipeRemainder())) {
			return InteractionType.TAKE_ITEM_FROM_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
