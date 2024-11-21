//? if creeper-overhaul {
package mod.crend.dynamiccrosshair.compat.mixin.creeperoverhaul;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.Item;
import net.minecraft.item.ShearsItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;

@Mixin(value = BaseCreeper.class, remap = false)
public abstract class BaseCreeperMixin implements DynamicCrosshairEntity {

	@Shadow public abstract boolean isSheared();
	@Shadow @Final public CreeperType type;

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		Item item = context.getItem();
		if (this.type.melee() == 0 && item instanceof FlintAndSteelItem) {
			return InteractionType.USE_ITEM_ON_ENTITY;
		}
		if (this.type.isShearable() && this.isSheared() && item instanceof ShearsItem) {
			return InteractionType.USE_ITEM_ON_ENTITY;
		}
		return null;
	}
}
//?}
