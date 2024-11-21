//? if macaws_roofs {
package mod.crend.dynamiccrosshair.compat.mixin.mcwroofs;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.kikoz.mcwroofs.objects.gutters.RainGutter;
import net.kikoz.mcwroofs.objects.items.Hammer;
import net.kikoz.mcwroofs.objects.roofs.BaseRoof;
import net.kikoz.mcwroofs.objects.roofs.RoofGlass;
import net.kikoz.mcwroofs.objects.roofs.RoofTopNew;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = Hammer.class, remap = false)
public class HammerMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock()) {
			Block block = context.getBlock();
			if (block instanceof BaseRoof || block instanceof RoofGlass || block instanceof RoofTopNew || block instanceof RainGutter) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
