//? if macaws_lights {
package mod.crend.dynamiccrosshair.compat.mixin.mcwlights;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.kikoz.mcwlights.objects.LightBaseTall;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = LightBaseTall.class, remap = false)
public class LightBaseTallMixin extends Block implements DynamicCrosshairBlock {
	public LightBaseTallMixin(Settings settings) {
		super(settings);
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		LightBaseTall.LightPart part = context.getBlockState().get(LightBaseTall.PART);
		if (part == LightBaseTall.LightPart.BASE || part == LightBaseTall.LightPart.TOP) {
			if (asItem() != context.getItem()) {
				return InteractionType.INTERACT_WITH_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
