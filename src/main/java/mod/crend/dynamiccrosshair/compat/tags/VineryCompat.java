package mod.crend.dynamiccrosshair.compat.tags;

import mod.crend.dynamiccrosshair.compat.DCCProvider;
import mod.crend.dynamiccrosshair.compat.version.VersionedTagBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class VineryCompat extends DCCProvider {
	public VineryCompat() {
		super("vinery");
	}

	@Override
	public void blocksAlwaysInteractable(VersionedTagBuilder<Block> builder) {
		//? if vinery {
		builder
				.addOptional(of("fermentation_barrel"))
				.addOptional(of("apple_press"))
				.addOptional(of("basket"))
				;
		//?}
	}

	@Override
	public void itemsThrowable(VersionedTagBuilder<Item> builder) {
		//? if vinery {
		builder
				.addOptional(of("rotten_cherry"))
				;
		//?}
	}
}
