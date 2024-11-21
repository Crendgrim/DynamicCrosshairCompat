//? if wetlands {
package mod.crend.dynamiccrosshair.compat.mixin.wetlands;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairEntity;
import net.birchfolks.wetlands.entity.custom.WispEntity;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = WispEntity.class, remap = false)
public class WispEntityMixin implements DynamicCrosshairEntity {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItemStack().isOf(Items.GLASS_BOTTLE)) {
			return InteractionType.USE_ITEM_ON_ENTITY;
		}
		return InteractionType.EMPTY;
	}
}
//?}
