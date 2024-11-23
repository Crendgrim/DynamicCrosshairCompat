//? if farmers-delight-refabricated {
package mod.crend.dynamiccrosshair.compat.mixin.farmersdelight;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.registry.tag.ItemTags;
import org.spongepowered.asm.mixin.Mixin;
import vectorwing.farmersdelight.common.block.SkilletBlock;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;
import java.util.Optional;

@Mixin(value = SkilletBlock.class, remap = false)
public class SkilletBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof SkilletBlockEntity skilletEntity) {
			ItemStack heldStack = context.getItemStack();
			//? if <1.21 {
			if (heldStack.isIn(ItemTags.SHOVELS) && skilletEntity.isCooking()) {
				if ((float) skilletEntity.lastFlippedTime + 20.0F < (float) context.getWorld().getTime()) {
					return InteractionType.USE_ITEM_ON_BLOCK;
				}
				return InteractionType.EMPTY;
			}
			//?}

			if (heldStack.isEmpty()) {
				return InteractionType.INTERACT_WITH_BLOCK;
			}

			var recipe = ((SkilletBlockEntityMixin) skilletEntity).invokeGetMatchingRecipe(/*? if <1.21 {*/new SimpleInventory(heldStack)/*?} else {*//*heldStack*//*?}*/);
			if (recipe.isPresent()) {
				return InteractionType.PLACE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
