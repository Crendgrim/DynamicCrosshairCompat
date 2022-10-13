package mod.crend.dynamiccrosshair.compat.rings;

import com.kwpugh.ring_of_teleport.ItemRingTeleport;
import com.kwpugh.ring_of_teleport.RingOfTeleport;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.ItemStack;

public class ApiImplRingOfTeleport implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return RingOfTeleport.MOD_ID;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof ItemRingTeleport;
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		ItemStack stack = context.getItemStack();
		if (stack.getItem() instanceof ItemRingTeleport) {
			if (context.isWithBlock()) {
				if (ItemRingTeleport.getPosition(stack) == null && context.player.isSneaking()) {
					return Crosshair.USE_ITEM;
				}
			} else if (ItemRingTeleport.getPosition(stack) != null) {
				return Crosshair.USE_ITEM;
			}

		}
		return null;
	}
}
