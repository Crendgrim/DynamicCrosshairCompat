//? if industrial-revolution {
package mod.crend.dynamiccrosshair.compat.mixin.indrev;

import me.steven.indrev.api.machines.Tier;
import me.steven.indrev.blocks.machine.MachineBlock;
import me.steven.indrev.items.misc.IRMachineUpgradeItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = IRMachineUpgradeItem.class, remap = false)
public class IRMachineUpgradeItemMixin implements DynamicCrosshairItem {
	@Shadow @Final private Tier from;

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock()) {
			BlockState blockState = context.getBlockState();
			if (blockState.getBlock() instanceof MachineBlock machineBlock && machineBlock.getTier() == from) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
