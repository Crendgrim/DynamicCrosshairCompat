//? if happiness-is-a-warm-gun {
package mod.crend.dynamiccrosshair.compat.mixin.hwg;

import mod.azure.hwg.item.weapons.HWGGunLoadedBase;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairRangedItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HWGGunLoadedBase.class)
public class HWGGunLoadedBaseMixin implements DynamicCrosshairItem, DynamicCrosshairRangedItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		//? if <1.20.6 {
		ItemStack itemStack = context.getItemStack();
		if (itemStack.getNbt() != null && itemStack.getNbt().getBoolean("Charged")) {
			if (itemStack.getDamage() < itemStack.getMaxDamage() - 1) {
				return InteractionType.RANGED_WEAPON;
			}
		} else if (!context.getPlayer().getProjectileType(itemStack).isEmpty()) {
			return InteractionType.USE_ITEM;
		}
		return InteractionType.EMPTY;
		//?} else
		/*return InteractionType.RANGED_WEAPON;*/
	}

	@Override
	public boolean dynamiccrosshair$isCharging(CrosshairContext context) {
		return context.isActiveItem() && context.getPlayer().getItemUseTimeLeft() > 0;
	}

	@Override
	public boolean dynamiccrosshair$isCharged(CrosshairContext context) {
		return HWGGunLoadedBase.getPullProgress(context.getPlayer().getItemUseTime()) == 1.0f;
	}
}
//?}
