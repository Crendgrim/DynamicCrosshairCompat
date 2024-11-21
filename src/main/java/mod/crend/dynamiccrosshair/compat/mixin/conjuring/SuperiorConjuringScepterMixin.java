//? if conjuring {
package mod.crend.dynamiccrosshair.compat.mixin.conjuring;

import com.glisco.conjuring.blocks.soulfire_forge.SoulfireForgeBlockEntity;
import com.glisco.conjuring.items.SuperiorConjuringScepter;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = SuperiorConjuringScepter.class, remap = false)
public class SuperiorConjuringScepterMixin extends ConjuringScepterMixin {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getPlayer().isSneaking()
				&& context.isWithBlock()
				&& context.getBlockEntity() instanceof SoulfireForgeBlockEntity forge
				&& forge.isRunning()
		) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}

		return super.dynamiccrosshair$compute(context);
	}
}
//?}
