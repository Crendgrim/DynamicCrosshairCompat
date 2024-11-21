//? if galosphere {
package mod.crend.dynamiccrosshair.compat.mixin.galosphere;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.world.World;
import net.orcinus.galosphere.entities.Sparkle;
import net.orcinus.galosphere.init.GItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = Sparkle.class, remap = false)
public abstract class SparkleMixin extends AnimalEntity implements DynamicCrosshairEntity {
	protected SparkleMixin(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow public abstract Sparkle.CrystalType getVariant();

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (getVariant() != Sparkle.CrystalType.NONE && itemStack.getItem() instanceof PickaxeItem && !isBaby()) {
			return InteractionType.USE_ITEM_ON_ENTITY;
		} else if (getVariant() == Sparkle.CrystalType.NONE && itemStack.isIn(GItemTags.SPARKLE_TEMPT_ITEMS)) {
			return InteractionType.USE_ITEM_ON_ENTITY;
		}
		return InteractionType.EMPTY;
	}
}
//?}
