//? if boat-container {
package mod.crend.dynamiccrosshair.compat.mixin.boatcontainer;

import de.kxmischesdomi.boatcontainer.common.entity.BoatWithBlockEntity;
import de.kxmischesdomi.boatcontainer.common.entity.FurnaceBoatEntity;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = FurnaceBoatEntity.class, remap = false)
public abstract class FurnaceBoatEntityMixin extends BoatWithBlockEntity implements DynamicCrosshairEntity {
	public FurnaceBoatEntityMixin(EntityType<? extends BoatEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow @Final private static Ingredient INGREDIENT;
	@Shadow private short fuel;

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (INGREDIENT.test(context.getItemStack()) && (fuel + 3600 <= 32000)) {
			return InteractionType.USE_ITEM_ON_ENTITY;
		}
		if (!context.getPlayer().shouldCancelInteraction()) {
			return InteractionType.INTERACT_WITH_ENTITY;
		}
		return InteractionType.EMPTY;
	}
}
//?}
