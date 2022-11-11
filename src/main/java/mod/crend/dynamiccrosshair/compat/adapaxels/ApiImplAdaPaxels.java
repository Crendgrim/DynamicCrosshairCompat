package mod.crend.dynamiccrosshair.compat.adapaxels;

import com.brand.adapaxels.AdaPaxels;
import com.brand.adapaxels.paxels.base.PaxelItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.mixin.IAxeItemMixin;
import mod.crend.dynamiccrosshair.mixin.IShovelItemMixin;
import net.minecraft.block.Oxidizable;
import net.minecraft.item.HoneycombItem;
import net.minecraft.item.Item;

public class ApiImplAdaPaxels implements DynamicCrosshairApi {

	@Override
	public String getNamespace() {
		return AdaPaxels.MOD_ID;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (context.isWithBlock() && context.includeUsableItem()) {
			Item handItem = context.getItem();
			if (handItem instanceof PaxelItem) {
				if (IAxeItemMixin.getSTRIPPED_BLOCKS().get(context.getBlock()) != null
						|| Oxidizable.getDecreasedOxidationBlock(context.getBlock()).isPresent()
						|| HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get().get(context.getBlock()) != null) {
					return Crosshair.USABLE;
				}
				if (IShovelItemMixin.getPATH_STATES().get(context.getBlock()) != null) {
					return Crosshair.USABLE;
				}
			}
		}
		return null;
	}
}
