package mod.crend.dynamiccrosshair.compat.camsbackpacks;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.common.blocks.BackpackBlock;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IBlockInteractHandler;
import mod.crend.dynamiccrosshair.api.IUsableItemHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class ApiImplCamsBackpacks implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return CamsBackpacks.MOD_ID;
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public IBlockInteractHandler getBlockInteractHandler() {
		return (clientPlayerEntity, itemStack, blockPos, blockState) -> {
			if (blockState.getBlock() instanceof BackpackBlock) {
				return Crosshair.INTERACTABLE;
			}

			return null;
		};
	}

	IUsableItemHandler usableItemHandler = new CamsBackpacksUsableItemHandler();

	@Override
	public IUsableItemHandler getUsableItemHandler() {
		return usableItemHandler;
	}

	private static class CamsBackpacksUsableItemHandler implements IUsableItemHandler {
		@Override
		public Crosshair checkUsableItemOnBlock(ClientPlayerEntity player, ItemStack itemStack, BlockPos blockPos, BlockState blockState) {
			if (player.shouldCancelInteraction()) {
				ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
				if (!chest.isEmpty() && chest.getItem() instanceof BackpackItem) {
					return Crosshair.HOLDING_BLOCK;
				}
			}

			return null;
		}
	}
}
