package mod.crend.dynamiccrosshair.compat.tags;

import mod.crend.dynamiccrosshair.compat.DCCProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ArchonCompat extends DCCProvider {
	public ArchonCompat() {
		super("archon");
	}

	@Override
	public void itemsAlwaysUsable(FabricTagProvider<Item>.FabricTagBuilder builder) {
		//? if archon {
		builder
				.addOptional(of("experience_pouch"))
				.addOptional(of("super_experience_pouch"))
				.addOptional(of("grimoire"))
				.addOptional(of("lightning_bottle"))
		;
		//?}
	}

	@Override
	public void blocksAlwaysInteractable(FabricTagProvider<Block>.FabricTagBuilder builder) {
		//? if archon {
		builder
				.addOptional(of("scripture_table"))
		;
		//?}
	}
}
