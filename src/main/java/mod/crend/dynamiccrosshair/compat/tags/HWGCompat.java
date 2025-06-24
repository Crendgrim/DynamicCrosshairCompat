package mod.crend.dynamiccrosshair.compat.tags;

import mod.crend.dynamiccrosshair.compat.DCCProvider;
import mod.crend.dynamiccrosshair.compat.version.VersionedTagBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class HWGCompat extends DCCProvider {
	public HWGCompat() {
		super("hwg");
	}

	@Override
	public void itemsThrowable(VersionedTagBuilder<Item> builder) {
		//? if happiness-is-a-warm-gun {
		builder
				.addOptional(of("grenade_emp"))
				.addOptional(of("grenade_frag"))
				.addOptional(of("grenade_napalm"))
				.addOptional(of("grenade_smoke"))
				.addOptional(of("grenade_stun"))
				;
		//?}
	}

	@Override
	public void blocksAlwaysInteractable(VersionedTagBuilder<Block> builder) {
		//? if happiness-is-a-warm-gun {
		builder
				.addOptional(of("gun_table"))
		;
		//?}
	}
}
