package mod.crend.dynamiccrosshair.compat.macaw;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.kikoz.mcwdoors.MacawsDoors;
import net.kikoz.mcwdoors.objects.GarageDoor;
import net.kikoz.mcwdoors.objects.GarageRemote;
import net.minecraft.item.ItemStack;

public class ApiImplMacawsDoors implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return MacawsDoors.MOD_ID;
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		if (context.isWithBlock() && context.getItem() instanceof GarageRemote) {
			if (context.getBlock() instanceof GarageDoor && !context.player.shouldCancelInteraction()) {
				return Crosshair.USE_ITEM;
			}
		}

		return null;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
			return (itemStack.getItem() instanceof GarageRemote);
		}
}
