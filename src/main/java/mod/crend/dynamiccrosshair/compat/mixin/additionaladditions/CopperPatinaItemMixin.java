//? if addadd {
package mod.crend.dynamiccrosshair.compat.mixin.additionaladditions;

import dqu.additionaladditions.item.CopperPatinaItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.block.Block;
import net.minecraft.block.Oxidizable;
import org.spongepowered.asm.mixin.Mixin;
import java.util.Optional;

@Mixin(value = CopperPatinaItem.class, remap = false)
public class CopperPatinaItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock()) {
			Optional<Block> optional = Oxidizable.getIncreasedOxidationBlock(context.getBlock());
			if (optional.isPresent() && !context.getPlayer().isSneaking()) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
