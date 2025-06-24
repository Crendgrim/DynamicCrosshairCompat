package mod.crend.dynamiccrosshair.compat.tags;

import mod.crend.dynamiccrosshair.compat.DCCProvider;
import mod.crend.dynamiccrosshair.compat.version.VersionedTagBuilder;
import net.minecraft.item.Item;

public class MultiBedsCompat extends DCCProvider {
	public MultiBedsCompat() {
		super("multibeds");
	}

	@Override
	public void itemsAlwaysUsable(VersionedTagBuilder<Item> builder) {
		//? if multibeds {
		builder
				.addOptional(of("bed_kit"))
				.addOptional(of("ladder_tools"))
				.addOptional(of("embroidery_thread"))
		;
		//?}
	}
}
