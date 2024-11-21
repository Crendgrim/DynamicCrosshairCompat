package mod.crend.dynamiccrosshair.compat.tags;

import mod.crend.dynamiccrosshair.compat.DCCProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class EnderChestsCompat extends DCCProvider {
	public EnderChestsCompat() {
		super("enderchests");
	}

	@Override
	public void blocksAlwaysInteractable(FabricTagProvider<Block>.FabricTagBuilder builder) {
		//? if enderchests {
		builder
				.addOptional(of("ender_chest"))
		;
		//?}
	}

	@Override
	public void itemsAlwaysUsable(FabricTagProvider<Item>.FabricTagBuilder builder) {
		//? if enderchests {
		builder
				.addOptional(of("ender_bag"))
				.addOptional(of("ender_pouch"))
				;
		//?}
	}
}
