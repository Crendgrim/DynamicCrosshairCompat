//? if archon {
package mod.crend.dynamiccrosshair.compat.mixin.archon;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;
import safro.archon.item.ChannelerItem;
import safro.archon.recipe.ChannelingRecipe;
import safro.archon.registry.RecipeRegistry;
import safro.archon.util.ArchonUtil;

@Mixin(value = ChannelerItem.class, remap = false)
public class ChannelerItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock()) {
			ChannelingRecipe recipe = context.getWorld().getRecipeManager()
					.listAllOfType(RecipeRegistry.CHANNELING)
					.stream()
					.filter((entry) -> ChannelingRecipe.isValid(entry, context.getBlock()))
					.findFirst()
					.orElse(null);
			if (recipe != null && ArchonUtil.canRemoveMana(context.getPlayer(), recipe.getManaCost())) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
