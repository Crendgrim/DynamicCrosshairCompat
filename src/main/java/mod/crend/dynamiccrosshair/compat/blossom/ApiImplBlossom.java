package mod.crend.dynamiccrosshair.compat.blossom;

import com.yurisuika.blossom.Blossom;
import com.yurisuika.blossom.block.FloweringLeavesBlock;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IBlockInteractHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.ShearsItem;

public class ApiImplBlossom implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Blossom.MOD_ID;
	}

	@Override
	public IBlockInteractHandler getBlockInteractHandler() {
		return context -> {
			if (context.getBlock() instanceof FloweringLeavesBlock) {
				if (context.getItem() instanceof ShearsItem && context.getBlockState().get(FloweringLeavesBlock.AGE) == 7) {
					return Crosshair.USE_ITEM;
				}
			}

			return null;
		};
	}
}
