//? if bygone-nether {
package mod.crend.dynamiccrosshair.compat.mixin.bygonenether;

import com.izofar.bygonenether.entity.WarpedEnderMan;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairEntity;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = WarpedEnderMan.class, remap = false)
public abstract class WarpedEnderManMixin implements DynamicCrosshairEntity {
	@Shadow protected abstract boolean isReadyForShearing();

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItemStack().isOf(Items.SHEARS) && isReadyForShearing()) {
			return InteractionType.USE_ITEM_ON_ENTITY;
		}
		return InteractionType.EMPTY;
	}
}
//?}
