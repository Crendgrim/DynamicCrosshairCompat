package mod.crend.dynamiccrosshair.compat.tags;

import mod.crend.dynamiccrosshair.compat.DCCProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class VineryCompat extends DCCProvider {
	public VineryCompat() {
		super("vinery");
	}

	@Override
	public void blocksAlwaysInteractable(FabricTagProvider<Block>.FabricTagBuilder builder) {
		//? if vinery {
		builder
				.addOptional(of("fermentation_barrel"))
				.addOptional(of("apple_press"))
				.addOptional(of("basket"))
				;
		//?}
	}

	@Override
	public void itemsThrowable(FabricTagProvider<Item>.FabricTagBuilder builder) {
		//? if vinery {
		builder
				.addOptional(of("rotten_cherry"))
				;
		//?}
	}
}
