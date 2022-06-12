package mod.crend.dynamiccrosshair.compat.botania;

import mod.crend.dynamiccrosshair.api.IEntityHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import vazkii.botania.common.item.ItemCacophonium;

public class BotaniaEntityHandler implements IEntityHandler {
	@Override
	public Crosshair checkEntity(ClientPlayerEntity clientPlayerEntity, ItemStack itemStack, Entity entity) {
		Item item = itemStack.getItem();

		if (item instanceof ItemCacophonium) {
			if (entity instanceof MobEntity) {
				return Crosshair.USE_ITEM;
			}
		}

		return null;
	}
}
