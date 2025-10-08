//? if kibe {
package mod.crend.dynamiccrosshair.compat.mixin.kibe;

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

//? if <1.21 {
import io.github.lucaargolo.kibe.items.miscellaneous.Lasso;
//?} else {
/*import io.github.lucaargolo.kibe.KibeMod;
import io.github.lucaargolo.kibe.item.Lasso;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.registry.Registries;
*///?}

@Mixin(value = Lasso.class, remap = false)
public abstract class LassoMixin implements DynamicCrosshairItem {
	@Shadow public abstract boolean canStoreEntity(@NotNull EntityType<?> entityType);

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (context.isWithEntity()) {
			//? if <1.21 {
			if (itemStack.hasNbt() && !itemStack.getNbt().contains("Entity")) {
			//?} else {
			/*if (!itemStack.contains(DataComponentTypes.ENTITY_DATA)) {
			*///?}
				Entity entity = context.getEntity();
				if (entity instanceof MobEntity && this.canStoreEntity(entity.getType())) {
					//? if >=1.21
					/*if (!KibeMod.INSTANCE.getCONFIG().getMiscellaneousModule().getLassoDenyList().contains(Registries.ENTITY_TYPE.getId(entity.getType()).toString()))*/
						return InteractionType.PICK_UP_ENTITY;
				}
			}
		} else if (context.isWithBlock()) {
			//? if <1.21 {
			if (itemStack.hasNbt() && itemStack.getNbt().contains("Entity")) {
			//?} else {
			/*if (itemStack.contains(DataComponentTypes.ENTITY_DATA)) {
			*///?}
				return InteractionType.PLACE_ENTITY;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
