package mod.crend.dynamiccrosshair.compat.tags;

import mod.crend.dynamiccrosshair.compat.DCCProvider;
import mod.crend.dynamiccrosshair.compat.version.VersionedTagBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class NumismaticOverhaulCompat extends DCCProvider {
	public NumismaticOverhaulCompat() {
		super("numismatic-overhaul");
	}

	@Override
	public void itemsAlwaysUsable(VersionedTagBuilder<Item> builder) {
		//? if numismatic-overhaul {
		builder
				.addOptional(of("bronze_coin"))
				.addOptional(of("gold_coin"))
				.addOptional(of("silver_coin"))
				.addOptional(of("money_bag"))
		;
		//?}
	}

	@Override
	public void blocksAlwaysInteractable(VersionedTagBuilder<Block> builder) {
		//? if numismatic-overhaul {
		builder
				.addOptional(of("inexhaustible_shop"))
				.addOptional(of("piggy_bank"))
				.addOptional(of("shop"))
		;
		//?}
	}
}
