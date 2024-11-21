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
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = KitchenStovetopBlock.class, remap = false)
public class KitchenStovetopBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof StovetopBlockEntity stovetopBlockEntity) {
			if (stovetopBlockEntity.getRecipeFor(context.getItemStack()).isPresent()) {
				return InteractionType.PLACE_ITEM_ON_BLOCK;
			}
			for (ItemStack stack : stovetopBlockEntity.getItemsBeingCooked()) {
				if (!stack.isEmpty() && context.getWorld().getRecipeManager().getFirstMatch(RecipeType.CAMPFIRE_COOKING, new SimpleInventory(stack), context.getWorld()).isEmpty()) {
					return InteractionType.TAKE_ITEM_FROM_BLOCK;
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
