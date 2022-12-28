package mod.crend.dynamiccrosshair.compat.artifacts;

import artifacts.Artifacts;
import artifacts.common.item.curio.CurioItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.trinkets.TrinketsHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.ItemStack;

public class ApiImplArtifacts implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Artifacts.MODID;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof CurioItem;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();

		if (context.includeUsableItem() && itemStack.getItem() instanceof CurioItem && TrinketsHandler.canEquipTrinket(context)) {
			return Crosshair.USABLE;
		}
		// everlasting food, umbrella
		return null;
	}


}
