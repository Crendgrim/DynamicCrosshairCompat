//? if wetlands {
package mod.crend.dynamiccrosshair.compat.mixin.wetlands;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairEntity;
import net.birchfolks.wetlands.entity.custom.ElderGearGolem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ElderGearGolem.class, remap = false)
public abstract class ElderGearGolemMixin extends GolemEntity implements DynamicCrosshairEntity {
	protected ElderGearGolemMixin(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow public abstract boolean getUneared();

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItemStack().isOf(Items.SHEARS) && !getUneared()) {
			return InteractionType.USE_ITEM_ON_ENTITY;
		}
		return InteractionType.EMPTY;
	}
}
//?}
