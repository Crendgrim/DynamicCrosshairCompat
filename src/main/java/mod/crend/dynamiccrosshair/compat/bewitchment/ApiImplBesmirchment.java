package mod.crend.dynamiccrosshair.compat.bewitchment;

import dev.mrsterner.besmirchment.common.Besmirchment;
import dev.mrsterner.besmirchment.common.block.PhylacteryBlock;
import dev.mrsterner.besmirchment.common.block.entity.PhylacteryBlockEntity;
import dev.mrsterner.besmirchment.common.entity.LichGemItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;

public class ApiImplBesmirchment implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Besmirchment.MODID;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		ItemStack itemStack = context.getItemStack();
		if (blockState.getBlock() instanceof PhylacteryBlock && context.getBlockEntity() instanceof PhylacteryBlockEntity phylactery) {
			if (LichGemItem.isSouled(itemStack)) {
				if (phylactery.souls < PhylacteryBlockEntity.MAX_SOULS) {
					return Crosshair.USABLE;
				}
			} else {
				return Crosshair.INTERACTABLE;
			}
		}
		return null;
	}
}
