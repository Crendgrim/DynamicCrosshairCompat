package mod.crend.dynamiccrosshair.compat.camsbackpacks;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.common.blocks.BackpackBlock;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

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
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		if (context.getBlock() instanceof BackpackBlock) {
			return Crosshair.INTERACTABLE;
		}

		return null;
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		if (context.player.shouldCancelInteraction() && context.isEmptyHanded()) {
			ItemStack chest = context.player.getEquippedStack(EquipmentSlot.CHEST);
			if (!chest.isEmpty() && chest.getItem() instanceof BackpackItem) {
				return Crosshair.HOLDING_BLOCK;
			}
		}

		return null;
	}
}
