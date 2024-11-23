//? if vinery {
package mod.crend.dynamiccrosshair.compat.mixin.vinery;

import mod.crend.dynamiccrosshair.compat.helper.VineryHandler;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.satisfy.vinery.block.grape.GrapeBush;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = GrapeBush.class, remap = false)
public class GrapeBushMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return VineryHandler.fromBush(context.getBlockState(), context.getItemStack(), GrapeBush.AGE, 3);
	}
}
//?}
