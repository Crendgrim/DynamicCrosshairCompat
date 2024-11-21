//? if macaws_doors {
package mod.crend.dynamiccrosshair.compat.mixin.mcwdoors;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.kikoz.mcwdoors.objects.GarageDoor;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = GarageDoor.class, remap = false)
public class GarageDoorMixin extends Block implements DynamicCrosshairBlock {
	public GarageDoorMixin(Settings settings) {
		super(settings);
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (this.asItem() != context.getItem()) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
