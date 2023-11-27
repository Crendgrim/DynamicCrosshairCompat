package mod.crend.dynamiccrosshair.compat.macaw;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.kikoz.mcwwindows.MacawsWindows;
import net.kikoz.mcwwindows.objects.Blinds;
import net.kikoz.mcwwindows.objects.GothicWindow;
import net.kikoz.mcwwindows.objects.Window;
import net.kikoz.mcwwindows.util.Hammer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class ApiImplMacawsWindows implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return MacawsWindows.MOD_ID;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return (block instanceof Window || block instanceof GothicWindow || block instanceof Blinds);
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		Block block = context.getBlock();
		if (block instanceof Window || block instanceof GothicWindow) {
			if (context.getItem() instanceof Hammer) {
				return Crosshair.USABLE;
			}
			if (context.getItem() != block.asItem()) {
				return Crosshair.INTERACTABLE;
			}
		}
		if (block instanceof Blinds) {
			if (context.getItem() != block.asItem()) {
				return Crosshair.INTERACTABLE;
			}
		}

		return null;
	}
}
