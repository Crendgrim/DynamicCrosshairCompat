//? if friends-and-foes {
package mod.crend.dynamiccrosshair.compat.mixin.friendsandfoes;

import com.faboslav.friendsandfoes.entity.CopperGolemEntity;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = CopperGolemEntity.class, remap = false)
public abstract class CopperGolemEntityMixin extends GolemEntity implements DynamicCrosshairEntity {
	protected CopperGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow public abstract boolean isWaxed();
	@Shadow public abstract boolean isOxidized();
	@Shadow public abstract boolean isDegraded();

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		Item item = context.getItem();
		if (item == Items.COPPER_INGOT && getHealth() != getMaxHealth()) {
			return InteractionType.USE_ITEM_ON_ENTITY;
		}
		if (item == Items.HONEYCOMB && !isWaxed() && !isOxidized()) {
			return InteractionType.USE_ITEM_ON_ENTITY;
		}
		if (item instanceof AxeItem && (isWaxed() || isDegraded())) {
			return InteractionType.USE_ITEM_ON_ENTITY;
		}
		return InteractionType.EMPTY;
	}
}
//?}
