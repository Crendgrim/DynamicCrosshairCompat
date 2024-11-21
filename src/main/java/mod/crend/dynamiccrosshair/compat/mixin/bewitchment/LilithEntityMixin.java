//? if bewitchment {
package mod.crend.dynamiccrosshair.compat.mixin.bewitchment;


import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.common.entity.living.LilithEntity;
import moriyashiine.bewitchment.common.registry.BWObjects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = LilithEntity.class, remap = false)
public class LilithEntityMixin extends BWHostileEntityMixin {
	protected LilithEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (this.isAlive() && this.getTarget() == null && BewitchmentAPI.isVampire(context.getPlayer(), true)) {
			if (context.getItemStack().isOf(BWObjects.GARLIC)) {
				return InteractionType.USE_ITEM_ON_ENTITY;
			}
		}
		return super.dynamiccrosshair$compute(context);
	}
}
//?}
