//? if farmers-delight-refabricated {
package mod.crend.dynamiccrosshair.compat.mixin.farmersdelight;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CampfireCookingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;

import java.util.Optional;

//? if <1.21 {
import net.minecraft.inventory.Inventory;
//?} else
/*import net.minecraft.recipe.RecipeEntry;*/

@Mixin(value = SkilletBlockEntity.class, remap = false)
public interface SkilletBlockEntityMixin {
	//? if <1.21 {
	@Invoker
	Optional<CampfireCookingRecipe> invokeGetMatchingRecipe(Inventory recipeWrapper);
	//?} else {
	/*@Invoker
	Optional<RecipeEntry<CampfireCookingRecipe>> invokeGetMatchingRecipe(ItemStack stack);
	*///?}
}
//?}
