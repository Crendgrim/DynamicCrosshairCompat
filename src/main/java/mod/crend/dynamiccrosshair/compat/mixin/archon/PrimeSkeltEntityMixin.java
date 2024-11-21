//? if archon {
package mod.crend.dynamiccrosshair.compat.mixin.archon;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import safro.archon.entity.PrimeSkeltEntity;

@Mixin(value = PrimeSkeltEntity.class, remap = false)
public abstract class PrimeSkeltEntityMixin extends SkeltEntityMixin {
	protected PrimeSkeltEntityMixin(EntityType<? extends TameableEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getPlayer().isSneaking()) {
			if (context.getItemStack().isEmpty()) {
				if (hasStackEquipped(EquipmentSlot.HEAD)
						|| hasStackEquipped(EquipmentSlot.CHEST)
						|| hasStackEquipped(EquipmentSlot.LEGS)
						|| hasStackEquipped(EquipmentSlot.FEET)
				) {
					return InteractionType.TAKE_ITEM_FROM_ENTITY;
				}
			} else if (context.getItemStack().getItem() instanceof ArmorItem armor) {
				if (!hasStackEquipped(armor.getSlotType())) {
					return InteractionType.PLACE_ITEM_ON_ENTITY;
				}
			}
		}
		return super.dynamiccrosshair$compute(context);
	}
}
//?}
