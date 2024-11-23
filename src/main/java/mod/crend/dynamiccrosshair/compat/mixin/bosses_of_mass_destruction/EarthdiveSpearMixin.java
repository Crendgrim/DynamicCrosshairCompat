//? if bosses-of-mass-destruction {
package mod.crend.dynamiccrosshair.compat.mixin.bosses_of_mass_destruction;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairRangedItem;
import net.barribob.boss.item.EarthdiveSpear;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = EarthdiveSpear.class, remap = false)
public abstract class EarthdiveSpearMixin implements DynamicCrosshairItem, DynamicCrosshairRangedItem {
	@Shadow protected abstract boolean isCharged(ItemStack stack, int remainingUseTicks);

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (this.dynamiccrosshair$isCharging(context) || this.dynamiccrosshair$isCharged(context)) {
			return InteractionType.RANGED_WEAPON;
		}
		return InteractionType.MELEE_WEAPON;
	}

	@Override
	public boolean dynamiccrosshair$isCharging(CrosshairContext context) {
		return context.isActiveItem() && context.getPlayer().getItemUseTimeLeft() > 0;
	}

	@Override
	public boolean dynamiccrosshair$isCharged(CrosshairContext context) {
		return this.isCharged(context.getItemStack(), context.getPlayer().getItemUseTimeLeft());
	}
}
//?}
