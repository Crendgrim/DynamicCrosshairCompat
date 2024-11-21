package mod.crend.dynamiccrosshair.compat.tags;

import mod.crend.dynamiccrosshair.compat.DCCProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;

public class MultiBedsCompat extends DCCProvider {
	public MultiBedsCompat() {
		super("multibeds");
	}

	@Override
	public void itemsAlwaysUsable(FabricTagProvider<Item>.FabricTagBuilder builder) {
		//? if multibeds {
		builder
				.addOptional(of("bed_kit"))
				.addOptional(of("ladder_tools"))
				.addOptional(of("embroidery_thread"))
		;
		//?}
	}
}
