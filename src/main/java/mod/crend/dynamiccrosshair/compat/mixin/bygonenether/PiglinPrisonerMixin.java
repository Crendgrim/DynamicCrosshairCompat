//? if bygone-nether {
package mod.crend.dynamiccrosshair.compat.mixin.bygonenether;

import com.izofar.bygonenether.entity.PiglinPrisoner;
import com.izofar.bygonenether.entity.ai.PiglinPrisonerAi;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairEntity;
import net.minecraft.entity.mob.PiglinActivity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = PiglinPrisoner.class, remap = false)
public abstract class PiglinPrisonerMixin implements DynamicCrosshairEntity {
	@Shadow public abstract PiglinActivity getActivity();

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (PiglinPrisonerAi.canAdmire((PiglinPrisoner) context.getEntity(), context.getItemStack()) && this.getActivity() != PiglinActivity.ADMIRING_ITEM) {
			return InteractionType.USE_ITEM_ON_ENTITY;
		}
		return InteractionType.EMPTY;
	}
}
//?}
