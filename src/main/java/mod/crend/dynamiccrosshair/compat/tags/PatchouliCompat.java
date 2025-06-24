package mod.crend.dynamiccrosshair.compat.tags;

import mod.crend.dynamiccrosshair.compat.DCCProvider;
import mod.crend.dynamiccrosshair.compat.version.VersionedTagBuilder;
import net.minecraft.item.Item;

public class PatchouliCompat extends DCCProvider {
	public PatchouliCompat() {
		super("patchouli");
	}

	@Override
	public void itemsAlwaysUsable(VersionedTagBuilder<Item> builder) {
		builder.addOptional(of("guide_book"));
	}
}
