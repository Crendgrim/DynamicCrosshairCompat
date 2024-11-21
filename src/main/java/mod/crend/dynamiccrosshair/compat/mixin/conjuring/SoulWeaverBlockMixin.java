//? if conjuring {
package mod.crend.dynamiccrosshair.compat.mixin.conjuring;

import com.glisco.conjuring.blocks.soul_weaver.SoulWeaverBlock;
import com.glisco.conjuring.blocks.soul_weaver.SoulWeaverBlockEntity;
import com.glisco.conjuring.items.ConjuringItems;
import com.glisco.conjuring.items.ConjuringScepter;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = SoulWeaverBlock.class, remap = false)
public class SoulWeaverBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof SoulWeaverBlockEntity weaver && !weaver.isRunning()) {
			ItemStack itemStack = context.getItemStack();
			if (itemStack.getItem().equals(ConjuringItems.CONJURATION_ESSENCE) && !weaver.isLit()) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
			if (itemStack.getItem() instanceof ConjuringScepter) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
			if (weaver.getItem().isEmpty()) {
				if (!itemStack.isEmpty()) {
					return InteractionType.PLACE_ITEM_ON_BLOCK;
				}
			} else {
				return InteractionType.TAKE_ITEM_FROM_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
