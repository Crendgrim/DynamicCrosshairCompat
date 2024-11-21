//? if reborncore {
package mod.crend.dynamiccrosshair.compat.mixin.reborncore;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.fabric.CrosshairFluidContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import reborncore.api.ToolManager;
import reborncore.api.blockentity.IMachineGuiHandler;
import reborncore.api.blockentity.IUpgrade;
import reborncore.api.blockentity.IUpgradeable;
import reborncore.common.blockentity.MachineBaseBlockEntity;
import reborncore.common.blocks.BlockMachineBase;

@Mixin(value = BlockMachineBase.class, remap = false)
public abstract class BlockMachineBaseMixin implements DynamicCrosshairBlock {
	@Shadow public abstract IMachineGuiHandler getGui();

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof MachineBaseBlockEntity machineEntity) {
			if (machineEntity.getTank() != null && CrosshairFluidContext.canInteractWithFluidStorage(context, machineEntity.getTank())) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}
		ItemStack itemStack = context.getItemStack();
		if (ToolManager.INSTANCE.canHandleTool(itemStack)
				|| (itemStack.getItem() instanceof IUpgrade && context.getBlockEntity() instanceof IUpgradeable)
		) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return this.getGui() == null ? InteractionType.EMPTY : InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
