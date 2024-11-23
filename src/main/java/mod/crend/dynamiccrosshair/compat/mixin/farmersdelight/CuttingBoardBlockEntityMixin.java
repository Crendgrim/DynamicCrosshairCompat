//? if farmers-delight-refabricated {
package mod.crend.dynamiccrosshair.compat.mixin.farmersdelight;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;

import java.util.Optional;

//? if <1.21 {
import io.github.fabricators_of_create.porting_lib.transfer.item.RecipeWrapper;
//?} else
/*import net.minecraft.recipe.RecipeEntry;*/

@Mixin(value = CuttingBoardBlockEntity.class, remap = false)
public interface CuttingBoardBlockEntityMixin {
	//? if <1.21 {
	@Invoker
	Optional<CuttingBoardRecipe> invokeGetMatchingRecipe(RecipeWrapper recipeWrapper, ItemStack toolStack, @Nullable PlayerEntity player);
	//?} else {
	/*@Invoker
	Optional<RecipeEntry<CuttingBoardRecipe>> invokeGetMatchingRecipe(ItemStack toolStack, @Nullable PlayerEntity player);
	*///?}
}
//?}
