package mod.crend.dynamiccrosshair.compat.woof;

import mine.block.woof.Woof;
import mine.block.woof.register.block.DogBowlBlock;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;

public class ApiImplWoof implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "woof";
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack itemStack = context.getItemStack();

		if (block instanceof DogBowlBlock) {
			if (itemStack.isIn(Woof.MEATS)) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
