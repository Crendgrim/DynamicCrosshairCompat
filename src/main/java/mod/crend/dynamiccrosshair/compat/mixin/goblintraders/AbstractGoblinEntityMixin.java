//? if goblin-traders {
package mod.crend.dynamiccrosshair.compat.mixin.goblintraders;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.entity.TraderCreatureEntity;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = AbstractGoblinEntity.class, remap = false)
public abstract class AbstractGoblinEntityMixin extends TraderCreatureEntity implements DynamicCrosshairEntity {
	protected AbstractGoblinEntityMixin(EntityType<? extends PassiveEntity> type, World level) {
		super(type, level);
	}

	@Shadow public abstract boolean hasCustomer();
	@Shadow public abstract boolean isStunned();

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItemStack().isOf(Items.NAME_TAG)) {
			return InteractionType.USE_ITEM_ON_ENTITY;
		}
		if (isAlive()
				&& !hasCustomer()
				&& !isBaby()
				&& (isFireImmune() || !isOnFire())
				&& !isStunned()
				&& !getOffers().isEmpty()
		) {
			return InteractionType.INTERACT_WITH_ENTITY;
		}
		return InteractionType.EMPTY;
	}
}
//?}
