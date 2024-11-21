//? if adventurez {
package mod.crend.dynamiccrosshair.compat.mixin.adventurez;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairEntity;
import net.adventurez.entity.EnderWhaleEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EnderWhaleEntity.class, remap = false)
public class EnderWhaleEntityMixin extends FlyingEntity implements DynamicCrosshairEntity {
	protected EnderWhaleEntityMixin(EntityType<? extends FlyingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		Item item = context.getItem();
		if ((item == Items.CHORUS_FRUIT || item == Items.POPPED_CHORUS_FRUIT) && this.getMaxHealth() - this.getHealth() > 0.1F){
			return InteractionType.USE_ITEM_ON_ENTITY;
		}

		if (!this.hasPassengers() || this.getPassengerList().size() < 2) {
			return InteractionType.MOUNT_ENTITY;
		}

		return InteractionType.EMPTY;
	}
}
//?}
