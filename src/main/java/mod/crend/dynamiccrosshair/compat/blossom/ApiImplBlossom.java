package mod.crend.dynamiccrosshair.compat.blossom;

import dev.yurisuika.blossom.block.FloweringLeavesBlock;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.ShearsItem;

public class ApiImplBlossom implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "blossom";
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		if (context.getBlock() instanceof FloweringLeavesBlock) {
			if (context.getItem() instanceof ShearsItem && context.getBlockState().get(FloweringLeavesBlock.AGE) == 7) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
