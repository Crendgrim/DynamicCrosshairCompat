package mod.crend.dynamiccrosshair.compat.mcsa;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.ShearsItem;
import timefall.mcsa.Mcsa;
import timefall.mcsa.blocks.WhitePumpkinBlock;

public class ApiImplMCSA implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Mcsa.MOD_ID;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		if (context.getBlock() instanceof WhitePumpkinBlock) {
			if (context.getItem() instanceof ShearsItem) {
				return Crosshair.USABLE;
			}
		}
		return null;
	}
}
