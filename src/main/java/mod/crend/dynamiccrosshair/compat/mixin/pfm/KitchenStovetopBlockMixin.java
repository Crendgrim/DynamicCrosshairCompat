//? if paladins-furniture {
package mod.crend.dynamiccrosshair.compat.mixin.pfm;

import com.unlikepaladin.pfm.blocks.KitchenStovetopBlock;
import com.unlikepaladin.pfm.blocks.blockentities.StovetopBlockEntity;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
//? if >=1.21.3 {
/*import net.minecraft.recipe.RecipePropertySet;
*///?} else if >1.21 {
/*import net.minecraft.recipe.input.SingleStackRecipeInput;*/
//?}
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = KitchenStovetopBlock.class, remap = false)
public class KitchenStovetopBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof StovetopBlockEntity stovetopBlockEntity) {
			//? if <=1.20.6 {
			if (stovetopBlockEntity.getRecipeFor(context.getItemStack()).isPresent()) {
				return InteractionType.PLACE_ITEM_ON_BLOCK;
			}
			//?}
			for (ItemStack stack : stovetopBlockEntity.getItemsBeingCooked()) {
				if (!stack.isEmpty() && context.getWorld().getRecipeManager()
						//? if <=1.20.6 {
						.getFirstMatch(RecipeType.CAMPFIRE_COOKING, new SimpleInventory(stack), context.getWorld()).isEmpty()
						//?} else if <=1.21.1 {
						/*.getFirstMatch(RecipeType.CAMPFIRE_COOKING, new SingleStackRecipeInput(stack), context.getWorld()).isEmpty()*/
						//?} else
						/*.getPropertySet(RecipePropertySet.CAMPFIRE_INPUT).canUse(stack)*/
				) {
					return InteractionType.TAKE_ITEM_FROM_BLOCK;
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
