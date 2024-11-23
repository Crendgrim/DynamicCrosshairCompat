//? if friends-and-foes {
package mod.crend.dynamiccrosshair.compat.mixin.friendsandfoes;

import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = GlareEntity.class, remap = false)
public abstract class GlareEntityMixin extends TameableEntity implements DynamicCrosshairEntity {
	protected GlareEntityMixin(EntityType<? extends TameableEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow public abstract boolean isGrumpy();

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();
		if (item == Items.GLOW_BERRIES) {
			if (!isTamed() || (getHealth() < getMaxHealth() && !isBreedingItem(itemStack))) {
				return InteractionType.USE_ITEM_ON_ENTITY;
			}
		}
		if (isBreedingItem(itemStack)) {
			if (isBaby() || (getBreedingAge() == 0 && canEat())) {
				return InteractionType.USE_ITEM_ON_ENTITY;
			}
		} else if (isOwner(context.getPlayer()) && !isGrumpy()) {
			return InteractionType.INTERACT_WITH_ENTITY;
		}
		return InteractionType.EMPTY;
	}
}
//?}
