//? if ae2 {
package mod.crend.dynamiccrosshair.compat.mixin.ae2;

import appeng.items.tools.powered.ColorApplicatorItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ColorApplicatorItem.class, remap = false)
public class ColorApplicatorItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock()) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		if (context.isWithEntity()) {
			Entity entity = context.getEntity();
			if (entity instanceof SheepEntity sheep && !sheep.isSheared()) {
				return InteractionType.USE_ITEM_ON_ENTITY;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
