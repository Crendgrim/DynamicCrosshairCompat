package mod.crend.dynamiccrosshair.compat.macaw;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IBlockInteractHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.kikoz.mcwwindows.MacawsWindows;
import net.kikoz.mcwwindows.objects.Blinds;
import net.kikoz.mcwwindows.objects.GothicWindow;
import net.kikoz.mcwwindows.objects.Window;
import net.kikoz.mcwwindows.util.Hammer;
import net.minecraft.block.Block;

public class ApiImplMacawsWindows implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return MacawsWindows.MOD_ID;
	}

	@Override
	public IBlockInteractHandler getBlockInteractHandler() {
		return (player, itemStack, blockPos, blockState) -> {
			Block block = blockState.getBlock();
			if (block instanceof Window || block instanceof GothicWindow) {
				if (itemStack.getItem() instanceof Hammer) {
					return Crosshair.USE_ITEM;
				}
				if (itemStack.getItem() != block.asItem()) {
					return Crosshair.INTERACTABLE;
				}
			}
			if (block instanceof Blinds) {
				if (itemStack.getItem() != block.asItem()) {
					return Crosshair.INTERACTABLE;
				}
			}

			return null;
		};
	}
}
