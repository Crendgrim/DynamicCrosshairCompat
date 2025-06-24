package mod.crend.dynamiccrosshair.compat.tags;

import mod.crend.dynamiccrosshair.compat.DCCProvider;
import mod.crend.dynamiccrosshair.compat.version.VersionedTagBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;


public class EnderChestsCompat extends DCCProvider {
	public EnderChestsCompat() {
		super("enderchests");
	}

	@Override
	public void blocksAlwaysInteractable(VersionedTagBuilder<Block> builder) {
		//? if enderchests {
		builder
				.addOptional(of("ender_chest"))
		;
		//?}
	}

	@Override
	public void itemsAlwaysUsable(VersionedTagBuilder<Item> builder) {
		//? if enderchests {
		builder
				.addOptional(of("ender_bag"))
				.addOptional(of("ender_pouch"))
				;
		//?}
	}
}
