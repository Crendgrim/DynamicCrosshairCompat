package mod.crend.dynamiccrosshair.compat.musica;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.block.BlockState;
import wraith.musica.SongMixerBlock;

public class ApiImplMusica implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "musica";
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof SongMixerBlock;
	}
}
