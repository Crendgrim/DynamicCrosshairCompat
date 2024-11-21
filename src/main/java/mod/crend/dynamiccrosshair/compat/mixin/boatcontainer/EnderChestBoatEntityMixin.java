//? if boat-container {
package mod.crend.dynamiccrosshair.compat.mixin.boatcontainer;

import de.kxmischesdomi.boatcontainer.common.entity.BoatWithBlockEntity;
import de.kxmischesdomi.boatcontainer.common.entity.EnderChestBoatEntity;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EnderChestBoatEntity.class, remap = false)
public abstract class EnderChestBoatEntityMixin extends BoatWithBlockEntity implements DynamicCrosshairEntity {
	public EnderChestBoatEntityMixin(EntityType<? extends BoatEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getPlayer().shouldCancelInteraction() || canAddPassenger(context.getPlayer())) {
			return InteractionType.INTERACT_WITH_ENTITY;
		}
		return InteractionType.EMPTY;
	}
}
//?}
