//? if runes {
package mod.crend.dynamiccrosshair.compat.mixin.runes;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.runes.crafting.RuneCraftingBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = RuneCraftingBlock.class, remap = false)
public class RuneCraftingBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext crosshairContext) {
		return InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
