package mod.crend.dynamiccrosshair.compat.tags;

import mod.crend.dynamiccrosshair.compat.DCCProvider;
import mod.crend.dynamiccrosshair.compat.version.VersionedTagBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class BewitchmentCompat extends DCCProvider {
	public BewitchmentCompat() {
		super("bewitchment");
	}

	@Override
	public void itemsAlwaysUsable(VersionedTagBuilder<Item> builder) {
		//? if bewitchment {
		builder
				.addOptional(of("book_of_shadows"))
				.addOptional(of("waystone"))
				;
		//?}
	}

	@Override
	public void blocksAlwaysInteractable(VersionedTagBuilder<Block> builder) {
		//? if bewitchment {
		builder
				.addOptional(of("golden_glyph"))
				.addOptional(of("crystal_ball"))
				;
		//?}
	}
}
