package mod.crend.dynamiccrosshair.compat.tradingpost;

import fuzs.tradingpost.TradingPost;
import fuzs.tradingpost.world.level.block.TradingPostBlock;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import net.minecraft.block.BlockState;

public class ApiImplTradingPost implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return TradingPost.MOD_ID;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof TradingPostBlock;
	}
}
