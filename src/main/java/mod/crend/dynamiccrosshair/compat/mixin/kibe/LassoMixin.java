//? if kibe {
package mod.crend.dynamiccrosshair.compat.mixin.kibe;

import io.github.lucaargolo.kibe.items.miscellaneous.Lasso;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = Lasso.class, remap = false)
public abstract class LassoMixin implements DynamicCrosshairItem {
	@Shadow public abstract boolean canStoreEntity(@NotNull EntityType<?> entityType);

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (context.isWithEntity()) {
			if (itemStack.hasNbt() && !itemStack.getNbt().contains("Entity")) {
				Entity entity = context.getEntity();
				if (entity instanceof MobEntity && this.canStoreEntity(entity.getType())) {
					return InteractionType.PICK_UP_ENTITY;
				}
			}
		} else if (context.isWithBlock()) {
			if (itemStack.hasNbt() && itemStack.getNbt().contains("Entity")) {
				return InteractionType.PLACE_ENTITY;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
