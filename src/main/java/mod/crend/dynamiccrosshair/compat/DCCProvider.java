package mod.crend.dynamiccrosshair.compat;

import mod.crend.dynamiccrosshair.compat.version.VersionedTagBuilder;
import mod.crend.dynamiccrosshairapi.VersionUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public abstract class DCCProvider {
	private final String namespace;

	protected DCCProvider(String namespace) {
		this.namespace = namespace;
	}

	public void blocksAlwaysInteractable(VersionedTagBuilder<Block> builder) { }
	public void blocksAlwaysInteractableInCreativeMode(VersionedTagBuilder<Block> builder) { }

	public void entitiesAlwaysInteractable(VersionedTagBuilder<EntityType<?>> builder) { }

	public void itemsAlwaysUsable(VersionedTagBuilder<Item> builder) { }
	public void itemsAlwaysUsableOnBlock(VersionedTagBuilder<Item> builder) { }
	public void itemsAlwaysUsableOnEntity(VersionedTagBuilder<Item> builder) { }
	public void itemsAlwaysUsableOnMiss(VersionedTagBuilder<Item> builder) { }
	public void itemsThrowable(VersionedTagBuilder<Item> builder) { }

	public Identifier of(String path) {
		return of(namespace, path);
	}
	public Identifier of(String ns, String path) {
		return VersionUtils.getIdentifier(ns, path);
	}
}
