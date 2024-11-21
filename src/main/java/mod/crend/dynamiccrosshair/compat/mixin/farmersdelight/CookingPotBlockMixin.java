//? if farmers-delight-refabricated {
package mod.crend.dynamiccrosshair.compat.mixin.farmersdelight;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;
import vectorwing.farmersdelight.common.block.CookingPotBlock;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;

@Mixin(value = CookingPotBlock.class, remap = false)
public class CookingPotBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItemStack().isEmpty() && context.getPlayer().isSneaking()) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		if (context.getBlockEntity() instanceof CookingPotBlockEntity cookingPotBlockEntity) {
			if (cookingPotBlockEntity.isContainerValid(context.getItemStack()) && !cookingPotBlockEntity.getMeal().isEmpty()) {
				return InteractionType.PLACE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
