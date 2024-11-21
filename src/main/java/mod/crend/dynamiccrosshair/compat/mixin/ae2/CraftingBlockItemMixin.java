//? if applied-energistics-2 {
package mod.crend.dynamiccrosshair.compat.mixin.ae2;

import appeng.block.crafting.CraftingBlockItem;
import appeng.core.AEConfig;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = CraftingBlockItem.class, remap = false)
public class CraftingBlockItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (AEConfig.instance().isDisassemblyCraftingEnabled() && context.getPlayer().isSneaking()) {
			return InteractionType.USE_ITEM;
		}
		return InteractionType.EMPTY;
	}
}
//?}
