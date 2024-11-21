//? if macaws_roofs {
package mod.crend.dynamiccrosshair.compat.mixin.mcwroofs;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.kikoz.mcwroofs.objects.roofs.RoofGlass;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = RoofGlass.class, remap = false)
public class RoofGlassMixin extends Block implements DynamicCrosshairBlock {
	public RoofGlassMixin(Settings settings) {
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
