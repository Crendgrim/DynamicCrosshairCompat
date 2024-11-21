//? if croptopia {
package mod.crend.dynamiccrosshair.compat.mixin.croptopia;

import com.epherical.croptopia.blocks.LeafCropBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = LeafCropBlock.class, remap = false)
public class LeafCropBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext crosshairContext) {
		return null;
	}
}
//?}
