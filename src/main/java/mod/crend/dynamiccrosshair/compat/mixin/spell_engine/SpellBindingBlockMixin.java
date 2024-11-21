//? if spell-engine {
package mod.crend.dynamiccrosshair.compat.mixin.spell_engine;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.spell_engine.spellbinding.SpellBindingBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = SpellBindingBlock.class, remap = false)
public class SpellBindingBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext crosshairContext) {
		return InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
