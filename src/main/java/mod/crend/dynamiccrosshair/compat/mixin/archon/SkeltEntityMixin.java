//? if archon {
package mod.crend.dynamiccrosshair.compat.mixin.archon;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import safro.archon.entity.SkeltEntity;
import safro.archon.registry.ItemRegistry;

@Mixin(value = SkeltEntity.class, remap = false)
public abstract class SkeltEntityMixin extends TameableEntity implements DynamicCrosshairEntity {
	protected SkeltEntityMixin(EntityType<? extends TameableEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (isTamed() && isOwner(context.getPlayer()) && context.getItemStack().isOf(ItemRegistry.UNDEAD_STAFF)) {
			return InteractionType.INTERACT_WITH_ENTITY;
		}
		return InteractionType.EMPTY;
	}
}
//?}
