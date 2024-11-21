//? if industrial-revolution {
package mod.crend.dynamiccrosshair.compat.mixin.indrev;

import me.steven.indrev.blockentities.laser.CapsuleBlockEntity;
import me.steven.indrev.blocks.machine.CapsuleBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = CapsuleBlock.class, remap = false)
public class CapsuleBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isMainHand() && context.getBlockEntity() instanceof CapsuleBlockEntity capsuleEntity) {
			if (capsuleEntity.getInventory().get(0).isEmpty()) {
				return InteractionType.PLACE_ITEM_ON_BLOCK;
			}
			return InteractionType.TAKE_ITEM_FROM_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
