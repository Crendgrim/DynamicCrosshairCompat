//? if bosses-of-mass-destruction {
package mod.crend.dynamiccrosshair.compat.mixin.bosses_of_mass_destruction;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.barribob.boss.item.ChargedEnderPearlItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ChargedEnderPearlItem.class, remap = false)
public class ChargedEnderPearlItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return InteractionType.THROW_ITEM;
	}
}
//?}
