//? if conjuring {
package mod.crend.dynamiccrosshair.compat.mixin.conjuring;

import com.glisco.conjuring.blocks.BlackstonePedestalBlockEntity;
import com.glisco.conjuring.blocks.RitualCore;
import com.glisco.conjuring.items.ConjuringScepter;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ConjuringScepter.class, remap = false)
public class ConjuringScepterMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getPlayer().isSneaking()) {
			if (context.isWithBlock()) {
				BlockEntity blockEntity = context.getBlockEntity();
				if (blockEntity instanceof BlackstonePedestalBlockEntity pedestal && !pedestal.isActive()) {
					return InteractionType.USE_ITEM_ON_BLOCK;
				}
				if (blockEntity instanceof RitualCore) {
					return InteractionType.USE_ITEM_ON_BLOCK;
				}
			} else {
				if (ConjuringScepter.isLinking(context.getItemStack())) {
					return InteractionType.USE_ITEM_ON_BLOCK;
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
