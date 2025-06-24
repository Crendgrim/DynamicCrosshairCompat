package mod.crend.dynamiccrosshair.compat.tags;

import mod.crend.dynamiccrosshair.compat.DCCProvider;
import mod.crend.dynamiccrosshair.compat.version.VersionedTagBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class IndustrialRevolutionCompat extends DCCProvider {
	public IndustrialRevolutionCompat() {
		super("indrev");
	}

	@Override
	public void blocksAlwaysInteractable(VersionedTagBuilder<Block> builder) {
		//? if industrial-revolution {
		builder
				.addOptional(of("cabinet"))
				.addOptional(of("drill_top"))
				.addOptional(of("drill_middle"))
				.addOptional(of("drill_bottom"))
		;
		//?}
	}

	@Override
	public void itemsAlwaysUsable(VersionedTagBuilder<Item> builder) {
		//? if industrial-revolution {
		builder
				.addOptional(of("guide_book"))
		;
		//?}
	}

	@Override
	public void itemsAlwaysUsableOnBlock(VersionedTagBuilder<Item> builder) {
		super.itemsAlwaysUsableOnBlock(builder);
		//? if industrial-revolution {
		builder
				.addOptional(of("energy_reader"))
		;
		//?}
	}

	@Override
	public void itemsAlwaysUsableOnMiss(VersionedTagBuilder<Item> builder) {
		super.itemsAlwaysUsableOnMiss(builder);
		//? if industrial-revolution {
		builder
				.addOptional(of("servo_retriever"))
				.addOptional(of("servo_output"))
		;
		//?}
	}
}
