package mod.crend.dynamiccrosshair.compat.disenchanter;

import com.glisco.disenchanter.Disenchanter;
import com.glisco.disenchanter.DisenchanterBlock;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;

public class ApiImplDisenchanter implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Disenchanter.MOD_ID;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof DisenchanterBlock;
	}
}
