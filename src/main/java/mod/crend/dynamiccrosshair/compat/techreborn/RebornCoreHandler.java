package mod.crend.dynamiccrosshair.compat.techreborn;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import reborncore.api.ToolManager;
import reborncore.api.blockentity.IUpgrade;
import reborncore.api.blockentity.IUpgradeable;
import reborncore.common.blockentity.MachineBaseBlockEntity;
import reborncore.common.blocks.BlockMachineBase;

public class RebornCoreHandler {

	public static Crosshair checkBlockInteractable(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		BlockEntity blockEntity = context.getBlockEntity();
		if (block instanceof BlockMachineBase machine && blockEntity != null) {
			if (blockEntity instanceof MachineBaseBlockEntity machineEntity) {
				if (machineEntity.getTank() != null) { // TODO fluid handling
					return Crosshair.INTERACTABLE;
				}
			}
			ItemStack itemStack = context.getItemStack();
			if (ToolManager.INSTANCE.canHandleTool(itemStack)
					|| (itemStack.getItem() instanceof IUpgrade && context.getBlockEntity() instanceof IUpgradeable)
			) {
				return Crosshair.USE_ITEM;
			}

			if (machine.getGui() != null) {
				return Crosshair.INTERACTABLE;
			}
		}
		return null;
	}

	public static boolean isUsableItem(ItemStack itemStack) {
		return ToolManager.INSTANCE.canHandleTool(itemStack);
	}

	public static Crosshair checkUsableItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (ToolManager.INSTANCE.canHandleTool(itemStack)) {
			Block block = context.getBlock();
			if (block instanceof BlockMachineBase) {
				return Crosshair.USE_ITEM;
			}
		}
		return null;
	}

}
