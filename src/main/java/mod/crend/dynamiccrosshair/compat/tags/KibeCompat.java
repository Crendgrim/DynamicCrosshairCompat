package mod.crend.dynamiccrosshair.compat.tags;

import mod.crend.dynamiccrosshair.compat.DCCProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class KibeCompat extends DCCProvider {
	public KibeCompat() {
		super("kibe");
	}

	@Override
	public void blocksAlwaysInteractable(FabricTagProvider<Block>.FabricTagBuilder builder) {
		//? if kibe {
		builder
				.addOptional(of("big_torch"))
				.addOptional(of("breaker"))
				.addOptional(of("cooler"))
				.addOptional(of("drawbridge"))
				.addOptional(of("cobblestone_generator_mk1"))
				.addOptional(of("cobblestone_generator_mk2"))
				.addOptional(of("cobblestone_generator_mk3"))
				.addOptional(of("cobblestone_generator_mk4"))
				.addOptional(of("cobblestone_generator_mk5"))
				.addOptional(of("basalt_generator_mk1"))
				.addOptional(of("basalt_generator_mk2"))
				.addOptional(of("basalt_generator_mk3"))
				.addOptional(of("basalt_generator_mk4"))
				.addOptional(of("basalt_generator_mk5"))
				.addOptional(of("fluid_hopper"))
				.addOptional(of("redstone_timer"))
				.addOptional(of("placer"))
				.addOptional(of("tank"))
				.addOptional(of("trash_can"))
				.addOptional(of("vacuum_hopper"))
				.addOptional(of("wither_builder"))
		;
		//?}
	}

	@Override
	public void itemsAlwaysUsable(FabricTagProvider<Item>.FabricTagBuilder builder) {
		//? if kibe {
		builder
				.addOptional(of("entangled_bag"))
				.addOptional(of("white_glider"))
				.addOptional(of("orange_glider"))
				.addOptional(of("magenta_glider"))
				.addOptional(of("light_blue_glider"))
				.addOptional(of("yellow_glider"))
				.addOptional(of("lime_glider"))
				.addOptional(of("pink_glider"))
				.addOptional(of("gray_glider"))
				.addOptional(of("light_gray_glider"))
				.addOptional(of("cyan_glider"))
				.addOptional(of("blue_glider"))
				.addOptional(of("purple_glider"))
				.addOptional(of("green_glider"))
				.addOptional(of("brown_glider"))
				.addOptional(of("red_glider"))
				.addOptional(of("black_glider"))
				.addOptional(of("pocket_crafting_table"))
				.addOptional(of("pocket_trash_can"))
		;
		//?}
	}
}
