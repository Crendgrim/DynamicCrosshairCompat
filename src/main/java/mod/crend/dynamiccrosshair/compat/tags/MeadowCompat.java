package mod.crend.dynamiccrosshair.compat.tags;

import mod.crend.dynamiccrosshair.compat.DCCProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;

public class MeadowCompat extends DCCProvider {
	public MeadowCompat() {
		super("meadow");
	}

	@Override
	public void blocksAlwaysInteractable(FabricTagProvider<Block>.FabricTagBuilder builder) {
		//? if meadow {
		builder
				.addOptional(of("cheese_form"))
				.addOptional(of("cooking_cauldron"))
				.addOptional(of("camera"))
				.addOptional(of("cheesecake"))
				.addOptional(of("cheese_tart"))
				.addOptional(of("cheese_block"))
				.addOptional(of("amethyst_cheese_block"))
				.addOptional(of("buffalo_cheese_block"))
				.addOptional(of("warped_cheese_block"))
				.addOptional(of("grain_cheese_block"))
				.addOptional(of("sheep_cheese_block"))
				.addOptional(of("goat_cheese_block"))
				.addOptional(of("fondue"))
				.addOptional(of("shutter_block"))
				.addOptional(of("shutter_block_poppy"))
				.addOptional(of("shutter_block_fir"))
				.addOptional(of("shutter_block_berry"))
				.addOptional(of("woodcutter"))
		;
		//?}
	}
}
