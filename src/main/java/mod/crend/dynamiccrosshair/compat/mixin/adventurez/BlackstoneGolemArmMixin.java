//? if adventurez {
package mod.crend.dynamiccrosshair.compat.mixin.adventurez;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairRangedItem;
import net.adventurez.item.BlackstoneGolemArm;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BlackstoneGolemArm.class, remap = false)
public abstract class BlackstoneGolemArmMixin extends Item implements DynamicCrosshairRangedItem {
	public BlackstoneGolemArmMixin(Settings settings) {
		super(settings);
	}

	@Override
	public boolean dynamiccrosshair$isCharging(CrosshairContext context) {
		return context.isActiveItem() && context.getPlayer().getItemUseTimeLeft() > 0;
	}

	@Override
	public boolean dynamiccrosshair$isCharged(CrosshairContext context) {
		int stoneCounter = this.getMaxUseTime(context.getItemStack()) - context.getPlayer().getItemUseTimeLeft();
		return stoneCounter >= 30;
	}
}
//?}
