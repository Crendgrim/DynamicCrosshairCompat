//? if macaws_lights {
package mod.crend.dynamiccrosshair.compat.mixin.mcwlights;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.kikoz.mcwlights.objects.LightBaseShort;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = LightBaseShort.class, remap = false)
public class LightBaseShortMixin extends Block implements DynamicCrosshairBlock {
	public LightBaseShortMixin(Settings settings) {
		super(settings);
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (asItem() != context.getItem()) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
