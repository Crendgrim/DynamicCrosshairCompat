//? if macaws_windows {
package mod.crend.dynamiccrosshair.compat.mixin.mcwwindows;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.kikoz.mcwwindows.objects.Blinds;
import net.kikoz.mcwwindows.objects.GothicWindow;
import net.kikoz.mcwwindows.objects.Window;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = {
		Blinds.class,
		GothicWindow.class,
		Window.class
}, remap = false)
public class WindowMixin extends Block implements DynamicCrosshairBlock {
	public WindowMixin(Settings settings) {
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
