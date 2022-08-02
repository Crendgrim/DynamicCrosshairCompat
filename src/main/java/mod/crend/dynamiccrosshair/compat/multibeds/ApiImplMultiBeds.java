package mod.crend.dynamiccrosshair.compat.multibeds;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import shetiphian.multibeds.MultiBeds;
import shetiphian.multibeds.common.block.BlockMultiBedBase;
import shetiphian.multibeds.common.item.ItemBedCustomization;
import shetiphian.multibeds.common.item.ItemBeddingPackage;
import shetiphian.multibeds.common.item.ItemBuilderKit;
import shetiphian.multibeds.common.item.ItemEmbroideryThread;

public class ApiImplMultiBeds implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return MultiBeds.MOD_ID;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		Item item = context.getItem();
		if (context.getBlock() instanceof BlockMultiBedBase) {
			if (item instanceof ItemBedCustomization || item instanceof ItemBeddingPackage) {
				return Crosshair.USE_ITEM;
			}
			return Crosshair.INTERACTABLE;
		}

		return null;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof ItemEmbroideryThread || itemStack.getItem() instanceof ItemBuilderKit;
	}
}
