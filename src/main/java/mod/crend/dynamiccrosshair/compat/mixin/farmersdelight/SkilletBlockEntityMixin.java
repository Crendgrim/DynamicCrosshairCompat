//? if farmers-delight-refabricated {
package mod.crend.dynamiccrosshair.compat.mixin.farmersdelight;

import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.CampfireCookingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;

import java.util.Optional;

@Mixin(value = SkilletBlockEntity.class, remap = false)
public interface SkilletBlockEntityMixin {
	@Invoker
	Optional<CampfireCookingRecipe> invokeGetMatchingRecipe(Inventory recipeWrapper);
}
//?}
