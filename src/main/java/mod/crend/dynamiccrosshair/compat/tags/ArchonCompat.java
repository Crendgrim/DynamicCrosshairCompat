package mod.crend.dynamiccrosshair.compat.tags;

import mod.crend.dynamiccrosshair.compat.DCCProvider;
import mod.crend.dynamiccrosshair.compat.version.VersionedTagBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ArchonCompat extends DCCProvider {
	public ArchonCompat() {
		super("archon");
	}

	@Override
	public void itemsAlwaysUsable(VersionedTagBuilder<Item> builder) {
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
	public void blocksAlwaysInteractable(VersionedTagBuilder<Block> builder) {
		//? if archon {
		builder
				.addOptional(of("scripture_table"))
		;
		//?}
	}
}
