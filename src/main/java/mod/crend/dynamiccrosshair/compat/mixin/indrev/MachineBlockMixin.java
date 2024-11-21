//? if industrial-revolution {
package mod.crend.dynamiccrosshair.compat.mixin.indrev;

import me.steven.indrev.IndustrialRevolution;
import me.steven.indrev.blockentities.MachineBlockEntity;
import me.steven.indrev.blocks.machine.MachineBlock;
import me.steven.indrev.items.misc.IRMachineUpgradeItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.fabric.CrosshairFluidContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = MachineBlock.class, remap = false)
public class MachineBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof MachineBlockEntity<?> blockEntity) {
			if (blockEntity.getFluidComponent() != null) {
				if (CrosshairFluidContext.canInteractWithFluidStorage(context, blockEntity.getFluidComponent())) {
					return InteractionType.USE_ITEM_ON_BLOCK;
				}
			}
			if (!(context.getItem() instanceof IRMachineUpgradeItem)) {
				ItemStack itemStack = context.getItemStack();
				if (itemStack.isIn(IndustrialRevolution.INSTANCE.getWRENCH_TAG())
						|| itemStack.isIn(IndustrialRevolution.INSTANCE.getSCREWDRIVER_TAG())) {
					return InteractionType.USE_ITEM_ON_BLOCK;
				}
				return InteractionType.INTERACT_WITH_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
