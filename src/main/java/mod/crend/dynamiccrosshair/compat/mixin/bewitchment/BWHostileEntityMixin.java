//? if bewitchment {
package mod.crend.dynamiccrosshair.compat.mixin.bewitchment;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairEntity;
import moriyashiine.bewitchment.common.entity.DemonMerchant;
import moriyashiine.bewitchment.common.entity.living.util.BWHostileEntity;
import moriyashiine.bewitchment.common.misc.BWUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BWHostileEntity.class, remap = false)
public class BWHostileEntityMixin extends HostileEntity implements DynamicCrosshairEntity {
	protected BWHostileEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getEntity() instanceof DemonMerchant merchant) {
			if (this.isAlive() && this.getTarget() == null) {
				if (BWUtil.rejectTrades(this)) {
					return InteractionType.EMPTY;
				}

				if (!merchant.getOffers().isEmpty()) {
					return InteractionType.INTERACT_WITH_ENTITY;
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
