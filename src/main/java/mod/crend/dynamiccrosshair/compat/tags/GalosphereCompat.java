package mod.crend.dynamiccrosshair.compat.tags;

import mod.crend.dynamiccrosshair.compat.DCCProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class GalosphereCompat extends DCCProvider {
	public GalosphereCompat() {
		super("galosphere");
	}

	@Override
	public void itemsAlwaysUsable(FabricTagProvider<Item>.FabricTagBuilder builder) {
		//? if galosphere {
		builder
				.addOptional(of("silver_bomb"))
		;
		//?}
	}

	@Override
	public void itemsAlwaysUsableOnBlock(FabricTagProvider<Item>.FabricTagBuilder builder) {
		//? if galosphere {
		builder
				.addOptional(of("glow_flare"))
				.addOptional(of("spectre_bottle"))
		;
		//?}
	}

	@Override
	public void blocksAlwaysInteractable(FabricTagProvider<Block>.FabricTagBuilder builder) {
		//? if galosphere {
		builder
				.addOptional(of("combustion_table"))
				;
		//?}
	}

}
