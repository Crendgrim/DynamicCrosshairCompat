//? if mariums-soulslike-weaponry {
package mod.crend.dynamiccrosshair.compat.mixin.soulsweapons;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.soulsweaponry.items.MoonstoneRing;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = MoonstoneRing.class, remap = false)
public class MoonstoneRingMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return InteractionType.USE_ITEM;
	}
}
//?}
