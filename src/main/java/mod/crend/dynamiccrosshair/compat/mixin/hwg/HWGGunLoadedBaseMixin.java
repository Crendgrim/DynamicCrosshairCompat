//? if happiness-is-a-warm-gun {
package mod.crend.dynamiccrosshair.compat.mixin.hwg;

import mod.azure.hwg.item.weapons.HWGGunLoadedBase;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HWGGunLoadedBase.class)
public class HWGGunLoadedBaseMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (itemStack.getNbt() != null && itemStack.getNbt().getBoolean("Charged")) {
			if (itemStack.getDamage() < itemStack.getMaxDamage() - 1) {
				return InteractionType.RANGED_WEAPON;
			}
		} else if (!context.getPlayer().getProjectileType(itemStack).isEmpty()) {
			return InteractionType.USE_ITEM;
		}
		return InteractionType.EMPTY;
	}
}
//?}
