package mod.crend.dynamiccrosshair.compat.macaw;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IBlockInteractHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.kikoz.mcwbridges.MacawsBridges;
import net.kikoz.mcwbridges.objects.Bridge_Base;
import net.kikoz.mcwbridges.objects.Plier;
import net.minecraft.item.Items;

public class ApiImplMacawsBridges implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return MacawsBridges.MOD_ID;
	}

	@Override
	public IBlockInteractHandler getBlockInteractHandler() {
		return (clientPlayerEntity, itemStack, blockPos, blockState) -> {
			if (blockState.getBlock() instanceof Bridge_Base) {
				if (itemStack.getItem() instanceof Plier) {
					return Crosshair.USE_ITEM;
				}
				if (blockState.get(Bridge_Base.TORCH)) {
					return Crosshair.INTERACTABLE;
				} else if (itemStack.isOf(Items.TORCH)) {
					return Crosshair.USE_ITEM;
				}
			}

			return null;
		};
	}
}
