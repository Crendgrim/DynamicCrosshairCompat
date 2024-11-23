//? if bewitchment {
package mod.crend.dynamiccrosshair.compat.mixin.bewitchment;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import moriyashiine.bewitchment.common.block.entity.interfaces.Lockable;
import moriyashiine.bewitchment.common.block.entity.interfaces.SigilHolder;
import moriyashiine.bewitchment.common.block.entity.interfaces.TaglockHolder;
import moriyashiine.bewitchment.common.item.AthameItem;
import moriyashiine.bewitchment.common.recipe.AthameStrippingRecipe;
import moriyashiine.bewitchment.common.registry.BWRecipeTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
//? if >1.20.1
/*import net.minecraft.recipe.RecipeEntry;*/
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = AthameItem.class, remap = false)
public class AthameItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock()) {
			BlockState blockState = context.getBlockState();
			AthameStrippingRecipe entry = context.getWorld().getRecipeManager()
					.listAllOfType(BWRecipeTypes.ATHAME_STRIPPING_RECIPE_TYPE)
					.stream()
					//? if >1.20.1
					/*.map(RecipeEntry::value)*/
					.filter((recipe) -> recipe.log == blockState.getBlock())
					.findFirst()
					.orElse(null);
			if (entry != null) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			} else {
				BlockEntity blockEntity;
				if (blockState.getBlock() instanceof DoorBlock && blockState.get(DoorBlock.HALF) == DoubleBlockHalf.UPPER) {
					blockEntity = context.getWorld().getBlockEntity(context.getBlockPos().down());
				} else {
					blockEntity = context.getBlockEntity();
				}
				if (blockEntity instanceof SigilHolder sigilHolder) {
					if (context.getPlayer().getUuid().equals(sigilHolder.getOwner())) {
						return InteractionType.USE_ITEM_ON_BLOCK;
					}
				} else if (blockEntity instanceof TaglockHolder taglockHolder) {
					if (context.getPlayer().getUuid().equals(taglockHolder.getOwner())) {
						return InteractionType.USE_ITEM_ON_BLOCK;
					}
				} else if (blockEntity instanceof Lockable lockable) {
					if (context.getPlayer().getUuid().equals(lockable.getOwner()) && !lockable.getEntities().isEmpty()) {
						return InteractionType.USE_ITEM_ON_BLOCK;
					}
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
