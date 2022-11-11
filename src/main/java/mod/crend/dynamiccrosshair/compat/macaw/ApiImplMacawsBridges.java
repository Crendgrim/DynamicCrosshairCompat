package mod.crend.dynamiccrosshair.compat.macaw;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.kikoz.mcwbridges.MacawsBridges;
import net.kikoz.mcwbridges.objects.Bridge_Base;
import net.kikoz.mcwbridges.objects.Plier;
import net.minecraft.block.BlockState;
import net.minecraft.item.Items;

public class ApiImplMacawsBridges implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return MacawsBridges.MOD_ID;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		if (blockState.getBlock() instanceof Bridge_Base) {
			if (context.getItem() instanceof Plier) {
				return Crosshair.USABLE;
			}
			if (blockState.get(Bridge_Base.TORCH)) {
				return Crosshair.INTERACTABLE;
			} else if (context.getItemStack().isOf(Items.TORCH)) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
