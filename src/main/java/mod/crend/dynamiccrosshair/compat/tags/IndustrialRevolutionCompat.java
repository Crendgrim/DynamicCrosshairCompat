package mod.crend.dynamiccrosshair.compat.tags;

import mod.crend.dynamiccrosshair.compat.DCCProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class IndustrialRevolutionCompat extends DCCProvider {
	public IndustrialRevolutionCompat() {
		super("indrev");
	}

	@Override
	public void blocksAlwaysInteractable(FabricTagProvider<Block>.FabricTagBuilder builder) {
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
	public void itemsAlwaysUsable(FabricTagProvider<Item>.FabricTagBuilder builder) {
		//? if industrial-revolution {
		builder
				.addOptional(of("guide_book"))
		;
		//?}
	}

	@Override
	public void itemsAlwaysUsableOnBlock(FabricTagProvider<Item>.FabricTagBuilder builder) {
		super.itemsAlwaysUsableOnBlock(builder);
		//? if industrial-revolution {
		builder
				.addOptional(of("energy_reader"))
		;
		//?}
	}

	@Override
	public void itemsAlwaysUsableOnMiss(FabricTagProvider<Item>.FabricTagBuilder builder) {
		super.itemsAlwaysUsableOnMiss(builder);
		//? if industrial-revolution {
		builder
				.addOptional(of("servo_retriever"))
				.addOptional(of("servo_output"))
		;
		//?}
	}
}
