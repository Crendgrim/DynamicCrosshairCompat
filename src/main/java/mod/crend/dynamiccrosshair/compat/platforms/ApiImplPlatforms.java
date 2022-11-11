package mod.crend.dynamiccrosshair.compat.platforms;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import shetiphian.platforms.Platforms;
import shetiphian.platforms.common.block.BlockPlatformBase;
import shetiphian.platforms.common.block.BlockPlatformFrame;
import shetiphian.platforms.common.item.ItemWrench;
import shetiphian.platforms.common.misc.EnumTorchType;
import shetiphian.platforms.common.misc.TileHelper;

public class ApiImplPlatforms implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Platforms.MOD_ID;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof ItemWrench;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		Item item = context.getItem();
		BlockState blockState = context.getBlockState();

		if (blockState.getBlock() instanceof BlockPlatformBase) {
			if (item instanceof ItemWrench) {
				return Crosshair.USABLE;
			}
		}
		if (blockState.getBlock() instanceof BlockPlatformFrame) {
			EnumTorchType torch = TileHelper.getTorch(blockState);
			if (item == Items.GLOWSTONE_DUST) {
				if (torch != null) {
					switch (torch) {
						case REDSTONE_OFF:
						case REDSTONE_ON:
						case LIGHT:
						case SOUL:
							return Crosshair.USABLE;
					}
				}
			} else if (torch == null) {
				if (item == Items.REDSTONE_TORCH || item == Items.TORCH || item == Items.SOUL_TORCH) {
					return Crosshair.USABLE;
				}

			}
		}

		return null;
	}
}
