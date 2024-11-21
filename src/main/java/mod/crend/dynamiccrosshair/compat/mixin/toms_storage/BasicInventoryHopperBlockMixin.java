//? if toms-storage-fabric {
package mod.crend.dynamiccrosshair.compat.mixin.toms_storage;

import com.tom.storagemod.block.BasicInventoryHopperBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BasicInventoryHopperBlock.class, remap = false)
public class BasicInventoryHopperBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return context.getItemStack().isEmpty() ? InteractionType.INTERACT_WITH_BLOCK : InteractionType.USE_BLOCK;
	}
}
//?}
