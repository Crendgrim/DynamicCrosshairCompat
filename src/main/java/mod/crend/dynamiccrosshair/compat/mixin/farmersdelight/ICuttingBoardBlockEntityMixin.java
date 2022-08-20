package mod.crend.dynamiccrosshair.compat.mixin.farmersdelight;

import com.nhoryzon.mc.farmersdelight.entity.block.CuttingBoardBlockEntity;
import com.nhoryzon.mc.farmersdelight.entity.block.inventory.RecipeWrapper;
import com.nhoryzon.mc.farmersdelight.recipe.CuttingBoardRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Optional;

@Mixin(value = CuttingBoardBlockEntity.class, remap = false)
public interface ICuttingBoardBlockEntityMixin {
	@Invoker
	Optional<CuttingBoardRecipe> invokeGetMatchingRecipe(RecipeWrapper recipeWrapper, ItemStack toolStack, @Nullable PlayerEntity player);
}
