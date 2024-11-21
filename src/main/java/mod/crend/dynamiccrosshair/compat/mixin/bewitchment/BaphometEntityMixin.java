//? if bewitchment {
package mod.crend.dynamiccrosshair.compat.mixin.bewitchment;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.common.entity.living.BaphometEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = BaphometEntity.class, remap = false)
public abstract class BaphometEntityMixin extends BWHostileEntityMixin {
	@Shadow public abstract String getPledgeID();

	protected BaphometEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (BewitchmentAPI.isPledged(context.getPlayer(), this.getPledgeID())) {
			return super.dynamiccrosshair$compute(context);
		}
		return InteractionType.EMPTY;
	}
}
//?}
