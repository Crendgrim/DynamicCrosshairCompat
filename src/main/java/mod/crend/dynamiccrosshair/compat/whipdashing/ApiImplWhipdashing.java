package mod.crend.dynamiccrosshair.compat.whipdashing;

import amymialee.whipdashing.Whipdashing;
import amymialee.whipdashing.entities.LatchEntity;
import amymialee.whipdashing.items.LatchItem;
import amymialee.whipdashing.items.WhipdashItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.item.Item;

public class ApiImplWhipdashing implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Whipdashing.MOD_ID;
	}

	@Override
	public Crosshair checkEntity(CrosshairContext context) {

		if (context.isMainHand()
				&& context.getEntity() instanceof LatchEntity
				&& context.getItem() instanceof WhipdashItem) {
			return Crosshair.CORRECT_TOOL;
		}

		return null;
	}

	@Override
	public Crosshair checkBlockItem(CrosshairContext context) {
		Item item = context.getItem();
		if (context.isWithBlock()
				&& item instanceof LatchItem
				&& context.world.isAir(context.getBlockPos().offset(context.getBlockHitSide()))) {
			return Crosshair.HOLDING_BLOCK;
		}

		return null;
	}
}
