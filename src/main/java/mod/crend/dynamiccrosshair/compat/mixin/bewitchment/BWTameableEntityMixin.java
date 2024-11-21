//? if bewitchment {
package mod.crend.dynamiccrosshair.compat.mixin.bewitchment;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairEntity;
import moriyashiine.bewitchment.common.entity.living.util.BWTameableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = BWTameableEntity.class, remap = false)
public abstract class BWTameableEntityMixin extends TameableEntity implements DynamicCrosshairEntity {
	@Shadow protected abstract boolean isTamingItem(ItemStack itemStack);

	protected BWTameableEntityMixin(EntityType<? extends TameableEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (this.isBreedingItem(itemStack)) {
			if (this.getHealth() < this.getMaxHealth()) {
				return InteractionType.USE_ITEM_ON_ENTITY;
			}
		} else if (!this.isTamed()) {
			if (this.isTamingItem(itemStack)) {
				return InteractionType.USE_ITEM_ON_ENTITY;
			}
		} else if (this.isOwner(context.getPlayer())) {
			return InteractionType.INTERACT_WITH_ENTITY;
		}
		return InteractionType.EMPTY;
	}
}
//?}
