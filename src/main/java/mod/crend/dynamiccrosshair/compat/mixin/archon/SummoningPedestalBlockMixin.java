//? if archon {
package mod.crend.dynamiccrosshair.compat.mixin.archon;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import safro.archon.block.SummoningPedestalBlock;
import safro.archon.block.entity.SummoningPedestalBlockEntity;

@Mixin(value = SummoningPedestalBlock.class, remap = false)
public class SummoningPedestalBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof SummoningPedestalBlockEntity pedestal) {
			if (pedestal.isIdle()) {
				ItemStack itemStack = context.getItemStack();
				if (context.getPlayer().isSneaking()) {
					return InteractionType.TAKE_ITEM_FROM_BLOCK;
				} else if (!itemStack.isEmpty() && !pedestal.hasItem(itemStack.getItem())) {
					return InteractionType.PLACE_ITEM_ON_BLOCK;
				} else {
					return InteractionType.INTERACT_WITH_BLOCK;
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
