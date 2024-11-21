package mod.crend.dynamiccrosshair.compat.tags;

//? if another-furniture
import com.starfish_studios.another_furniture.registry.AFBlockTags;
import mod.crend.dynamiccrosshair.compat.DCCProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;

public class AnotherFurnitureCompat extends DCCProvider {
	public AnotherFurnitureCompat() {
		super("another_furniture");
	}

	@Override
	public void blocksAlwaysInteractable(FabricTagProvider<Block>.FabricTagBuilder builder) {
		//? if another-furniture {
		builder
				.addOptionalTag(AFBlockTags.CURTAINS)
				.addOptionalTag(AFBlockTags.DRAWERS)
				.addOptionalTag(AFBlockTags.SHUTTERS)
		;
		//?}
	}
}
