//? if farmers-delight-refabricated {
package mod.crend.dynamiccrosshair.compat.mixin.farmersdelight;

//? if <1.21
import io.github.fabricators_of_create.porting_lib.transfer.item.RecipeWrapper;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import vectorwing.farmersdelight.common.block.CuttingBoardBlock;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.tag.ModTags;

@Mixin(value = CuttingBoardBlock.class, remap = false)
public class CuttingBoardBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof CuttingBoardBlockEntity cuttingBoardBlockEntity) {
			ItemStack heldStack = context.getItemStack();
			ItemStack offhandStack = context.getItemStack();
			if (cuttingBoardBlockEntity.isEmpty()) {
				// Logic copied from mod
				if (!offhandStack.isEmpty()) {
					if (context.isMainHand() && !offhandStack.isIn(ModTags.OFFHAND_EQUIPMENT) && !(heldStack.getItem() instanceof BlockItem)) {
						return InteractionType.EMPTY;
					}

					if (context.isOffHand() && offhandStack.isIn(ModTags.OFFHAND_EQUIPMENT)) {
						return InteractionType.EMPTY;
					}
				}

				if (heldStack.isEmpty()) {
					return InteractionType.EMPTY;
				}

				if (cuttingBoardBlockEntity.isEmpty() && !heldStack.isEmpty()) {
					return InteractionType.PLACE_ITEM_ON_BLOCK;
				}
			} else {
				if (!heldStack.isEmpty()) {
					if (!cuttingBoardBlockEntity.isItemCarvingBoard()) {
						var matchingRecipe = ((CuttingBoardBlockEntityMixin) cuttingBoardBlockEntity).invokeGetMatchingRecipe(
								//? if <1.21
								new RecipeWrapper(cuttingBoardBlockEntity.getInventory()),
								heldStack,
								context.getPlayer()
						);
						if (matchingRecipe.isPresent()) {
							return InteractionType.PLACE_ITEM_ON_BLOCK;
						}
					}
				} else if (context.isMainHand()) {
					return InteractionType.TAKE_ITEM_FROM_BLOCK;
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
