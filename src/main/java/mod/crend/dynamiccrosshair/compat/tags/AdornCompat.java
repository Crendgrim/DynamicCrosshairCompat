package mod.crend.dynamiccrosshair.compat.tags;

//? if adorn
import juuxel.adorn.lib.AdornTags;
import mod.crend.dynamiccrosshair.compat.DCCProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;

public class AdornCompat extends DCCProvider {
	public AdornCompat() {
		super("adorn");
	}

	@Override
	public void blocksAlwaysInteractable(FabricTagProvider<Block>.FabricTagBuilder builder) {
		//? if adorn {
		builder
				//? if <1.21.1 {
				.addOptionalTag(AdornTags.INSTANCE.getTABLE_LAMPS().getBlock())
				.addOptionalTag(AdornTags.INSTANCE.getKITCHEN_CUPBOARDS().getBlock())
				.addOptionalTag(AdornTags.INSTANCE.getDRAWERS().getBlock())
				//?} else {
				/*.addOptionalTag(AdornTags.TABLE_LAMPS.block())
				.addOptionalTag(AdornTags.KITCHEN_CUPBOARDS.block())
				.addOptionalTag(AdornTags.DRAWERS.block())
				*///?}
		;
		//?}
	}
}
