package mod.crend.dynamiccrosshair.compat.tags;

import mod.crend.dynamiccrosshair.compat.DCCProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;

public class ThingsCompat extends DCCProvider {
	public ThingsCompat() {
		super("things");
	}

	@Override
	public void itemsAlwaysUsable(FabricTagProvider<Item>.FabricTagBuilder builder) {
		//? if things {
		builder
				.addOptional(of("things_almanac"))
				.addOptional(of("displacement_tome"))
				.addOptional(of("item_magnet"))
				;
		//?}
	}
}
