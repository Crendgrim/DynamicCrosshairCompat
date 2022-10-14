package mod.crend.dynamiccrosshair.compat.editsign;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ApiImplEditSign implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "editsign";
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		if (context.getBlock() instanceof AbstractSignBlock) {
			ItemStack itemStack = context.getItemStack();
			if (itemStack.getItem() instanceof DyeItem || itemStack.isOf(Items.GLOW_INK_SAC) || itemStack.isOf(Items.INK_SAC)) {
				return null;
			}
			return Crosshair.INTERACTABLE;
		}

		return null;
	}
}
