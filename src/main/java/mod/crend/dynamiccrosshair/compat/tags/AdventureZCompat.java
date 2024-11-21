package mod.crend.dynamiccrosshair.compat.tags;

import mod.crend.dynamiccrosshair.compat.DCCProvider;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;

public class AdventureZCompat extends DCCProvider {
	public AdventureZCompat() {
		super("adventurez");
	}

	@Override
	public void itemsAlwaysUsable(FabricTagProvider<Item>.FabricTagBuilder builder) {
		//? if adventurez {
		builder
				.addOptional(of("ender_flute"))
				.addOptional(of("handbook"))
				.addOptional(of("source_stone"))
		;
		//?}
	}
}
