package mod.crend.dynamiccrosshair.compat;


import mod.crend.dynamiccrosshairapi.VersionUtils;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public abstract class DCCProvider {
	private final String namespace;

	protected DCCProvider(String namespace) {
		this.namespace = namespace;
	}

	public void blocksAlwaysInteractable(FabricTagProvider<Block>.FabricTagBuilder builder) { }
	public void blocksAlwaysInteractableInCreativeMode(FabricTagProvider<Block>.FabricTagBuilder builder) { }

	public void entitiesAlwaysInteractable(FabricTagProvider<EntityType<?>>.FabricTagBuilder builder) { }

	public void itemsAlwaysUsable(FabricTagProvider<Item>.FabricTagBuilder builder) { }
	public void itemsAlwaysUsableOnBlock(FabricTagProvider<Item>.FabricTagBuilder builder) { }
	public void itemsAlwaysUsableOnEntity(FabricTagProvider<Item>.FabricTagBuilder builder) { }
	public void itemsAlwaysUsableOnMiss(FabricTagProvider<Item>.FabricTagBuilder builder) { }
	public void itemsThrowable(FabricTagProvider<Item>.FabricTagBuilder builder) { }

	public Identifier of(String path) {
		return of(namespace, path);
	}
	public Identifier of(String ns, String path) {
		return VersionUtils.getIdentifier(ns, path);
	}
}
