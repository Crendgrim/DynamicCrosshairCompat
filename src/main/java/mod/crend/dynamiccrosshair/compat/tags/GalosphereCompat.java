package mod.crend.dynamiccrosshair.compat.tags;

import mod.crend.dynamiccrosshair.compat.DCCProvider;
import mod.crend.dynamiccrosshair.compat.version.VersionedTagBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class GalosphereCompat extends DCCProvider {
	public GalosphereCompat() {
		super("galosphere");
	}

	@Override
	public void itemsAlwaysUsable(VersionedTagBuilder<Item> builder) {
		//? if galosphere {
		builder
				.addOptional(of("silver_bomb"))
		;
		//?}
	}

	@Override
	public void itemsAlwaysUsableOnBlock(VersionedTagBuilder<Item> builder) {
		//? if galosphere {
		builder
				.addOptional(of("glow_flare"))
				.addOptional(of("spectre_bottle"))
		;
		//?}
	}

	@Override
	public void blocksAlwaysInteractable(VersionedTagBuilder<Block> builder) {
		//? if galosphere {
		builder
				.addOptional(of("combustion_table"))
				;
		//?}
	}

}
