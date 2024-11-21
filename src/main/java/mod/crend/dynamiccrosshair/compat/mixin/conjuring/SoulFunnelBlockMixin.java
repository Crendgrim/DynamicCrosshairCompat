//? if conjuring {
package mod.crend.dynamiccrosshair.compat.mixin.conjuring;

import com.glisco.conjuring.blocks.SoulFunnelBlock;
import com.glisco.conjuring.blocks.SoulFunnelBlockEntity;
import com.glisco.conjuring.items.ConjuringFocus;
import com.glisco.conjuring.items.ConjuringScepter;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = SoulFunnelBlock.class, remap = false)
public class SoulFunnelBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		BlockState blockState = context.getBlockState();
		if (itemStack.isEmpty() && context.getPlayer().isSneaking()) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		if (itemStack.isOf(Items.SOUL_SAND) && !blockState.get(SoulFunnelBlock.FILLED)) {
			return InteractionType.PLACE_ITEM_ON_BLOCK;
		}
		if (itemStack.getItem() instanceof ConjuringScepter) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		if (blockState.get(SoulFunnelBlock.FILLED)) {
			SoulFunnelBlockEntity funnel = (SoulFunnelBlockEntity) context.getBlockEntity();
			if (funnel.getItem().isEmpty()) {
				if (itemStack.getItem() instanceof ConjuringFocus) {
					return InteractionType.USE_ITEM_ON_BLOCK;
				}
			} else if (!funnel.isRitualRunning()) {
				return InteractionType.TAKE_ITEM_FROM_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
