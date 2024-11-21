//? if adapaxels {
package mod.crend.dynamiccrosshair.compat.mixin.adapaxels;

import com.brand.adapaxels.mixin.AxeItemAccessor;
import com.brand.adapaxels.mixin.ShovelItemAccessor;
import com.brand.adapaxels.paxels.base.PaxelItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.HoneycombItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = PaxelItem.class, remap = false)
public class PaxelItemMixin implements DynamicCrosshairItem {

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (AxeItemAccessor.getStrippedBlocks().get(context.getBlock()) != null
				|| Oxidizable.getDecreasedOxidationBlock(context.getBlock()).isPresent()
				|| HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get().get(context.getBlock()) != null) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		if (ShovelItemAccessor.getPathStates().get(context.getBlock()) != null) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}

}
//?}