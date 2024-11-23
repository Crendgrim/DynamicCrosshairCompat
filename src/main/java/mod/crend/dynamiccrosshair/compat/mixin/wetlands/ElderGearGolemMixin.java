//? if wetlands {
package mod.crend.dynamiccrosshair.compat.mixin.wetlands;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairEntity;
import net.birchfolks.wetlands.entity.custom.AbstractGearGolemEntity;
import net.birchfolks.wetlands.entity.custom.ElderGearGolem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ElderGearGolem.class, remap = false)
public abstract class ElderGearGolemMixin extends AbstractGearGolemEntity implements DynamicCrosshairEntity {
	protected ElderGearGolemMixin(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItemStack().isOf(Items.SHEARS) && !getUneared()) {
			return InteractionType.USE_ITEM_ON_ENTITY;
		}
		return InteractionType.EMPTY;
	}
}
//?}
