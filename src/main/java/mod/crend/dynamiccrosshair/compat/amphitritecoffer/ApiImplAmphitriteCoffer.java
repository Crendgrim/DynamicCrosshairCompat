package mod.crend.dynamiccrosshair.compat.amphitritecoffer;

import io.github.orlouge.amphitritecoffer.AmphitriteCofferBlock;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;

public class ApiImplAmphitriteCoffer implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "amphitritecoffer";
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof AmphitriteCofferBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		if (blockState.getBlock() instanceof AmphitriteCofferBlock) {
			if (blockState.get(Properties.WATERLOGGED) || blockState.get(AmphitriteCofferBlock.CHARGED)) {
				return Crosshair.INTERACTABLE;
			}
		}

		return null;
	}
}
