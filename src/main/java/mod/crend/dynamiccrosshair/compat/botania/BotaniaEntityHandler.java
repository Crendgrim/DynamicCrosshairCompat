package mod.crend.dynamiccrosshair.compat.botania;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.IEntityHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import vazkii.botania.common.item.ItemCacophonium;

public class BotaniaEntityHandler implements IEntityHandler {
	@Override
	public Crosshair checkEntity(CrosshairContext context) {
		Item item = context.getItem();

		if (item instanceof ItemCacophonium) {
			if (context.getEntity() instanceof MobEntity) {
				return Crosshair.USE_ITEM;
			}
		}

		return null;
	}
}
