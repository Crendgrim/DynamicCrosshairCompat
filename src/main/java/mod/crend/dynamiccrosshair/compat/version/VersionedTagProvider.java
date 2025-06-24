package mod.crend.dynamiccrosshair.compat.version;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public abstract class VersionedTagProvider {
	public static abstract class ItemTagProvider extends FabricTagProvider.ItemTagProvider {
		public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture, @Nullable BlockTagProvider blockTagProvider) {
			super(output, registriesFuture, blockTagProvider);
		}

		public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
			this(output, registriesFuture, null);
		}

		protected VersionedTagBuilder<Item> versionedTagBuilder(TagKey<Item> tagKey) {
			//? if <1.21.6 {
			return new VersionedTagBuilder<>(getOrCreateTagBuilder(tagKey));
			 //?} else {
			/*return new VersionedTagBuilder<>(getTagBuilder(tagKey), valueLookupBuilder(tagKey));
			*///?}
		}
	}

	public static abstract class BlockTagProvider extends FabricTagProvider.BlockTagProvider {
		public BlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
			super(output, registriesFuture);
		}

		protected VersionedTagBuilder<Block> versionedTagBuilder(TagKey<Block> tagKey) {
			//? if <1.21.6 {
			return new VersionedTagBuilder<>(getOrCreateTagBuilder(tagKey));
			 //?} else {
			/*return new VersionedTagBuilder<>(getTagBuilder(tagKey), valueLookupBuilder(tagKey));
			*///?}
		}
	}

	public static abstract class EntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {
		public EntityTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
			super(output, registriesFuture);
		}

		protected VersionedTagBuilder<EntityType<?>> versionedTagBuilder(TagKey<EntityType<?>> tagKey) {
			//? if <1.21.6 {
			return new VersionedTagBuilder<>(getOrCreateTagBuilder(tagKey));
			 //?} else {
			/*return new VersionedTagBuilder<>(getTagBuilder(tagKey), valueLookupBuilder(tagKey));
			*///?}
		}
	}
}
