package mod.crend.dynamiccrosshair.compat.tags;

import mod.crend.dynamiccrosshair.compat.DCCProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class BewitchmentCompat extends DCCProvider {
	public BewitchmentCompat() {
		super("bewitchment");
	}

	@Override
	public void itemsAlwaysUsable(FabricTagProvider<Item>.FabricTagBuilder builder) {
		//? if bewitchment {
		builder
				.addOptional(of("book_of_shadows"))
				.addOptional(of("waystone"))
				;
		//?}
	}

	@Override
	public void blocksAlwaysInteractable(FabricTagProvider<Block>.FabricTagBuilder builder) {
		//? if bewitchment {
		builder
				.addOptional(of("golden_glyph"))
				.addOptional(of("crystal_ball"))
				;
		//?}
	}
}
