//? if adventurez {
package mod.crend.dynamiccrosshair.compat.mixin.adventurez;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.adventurez.block.ChiseledPolishedBlackstoneHolder;
import net.adventurez.block.entity.ChiseledPolishedBlackstoneHolderEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.TagInit;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ChiseledPolishedBlackstoneHolder.class, remap = false)
public class ChiseledPolishedBlackstoneHolderMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof ChiseledPolishedBlackstoneHolderEntity blockEntity) {
			ItemStack blockStack = blockEntity.getStack(0);
			if (!blockStack.isEmpty()) {
				return InteractionType.TAKE_ITEM_FROM_BLOCK;
			} else if ((context.getItemStack().isIn(TagInit.HOLDER_ITEMS) || ConfigInit.CONFIG.allow_all_items_on_holder) && context.getWorld().getBlockState(context.getBlockPos().up()).isAir()) {
				return InteractionType.PLACE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
