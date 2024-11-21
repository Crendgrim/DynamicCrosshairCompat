//? if industrial-revolution {
package mod.crend.dynamiccrosshair.compat.mixin.indrev;

import me.steven.indrev.blockentities.storage.ChargePadBlockEntity;
import me.steven.indrev.blocks.machine.ChargePadBlock;
import me.steven.indrev.components.InventoryComponent;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ChargePadBlock.class, remap = false)
public class ChargePadBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof ChargePadBlockEntity chargePadEntity) {
			InventoryComponent ic = chargePadEntity.getInventoryComponent();
			if (ic == null) return InteractionType.EMPTY;
			if (!ic.getInventory().getStack(0).isEmpty()) {
				return InteractionType.TAKE_ITEM_FROM_BLOCK;
			}
		}
		return InteractionType.PLACE_ITEM_ON_BLOCK;
	}
}
//?}
