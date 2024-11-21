package mod.crend.dynamiccrosshair.compat;

import mod.crend.dynamiccrosshair.compat.tags.AdornCompat;
import mod.crend.dynamiccrosshair.compat.tags.AdventureZCompat;
import mod.crend.dynamiccrosshair.compat.tags.AnotherFurnitureCompat;
import mod.crend.dynamiccrosshair.compat.tags.ArchonCompat;
import mod.crend.dynamiccrosshair.compat.tags.BewitchmentCompat;
import mod.crend.dynamiccrosshair.compat.tags.EnderChestsCompat;
import mod.crend.dynamiccrosshair.compat.tags.GalosphereCompat;
import mod.crend.dynamiccrosshair.compat.tags.HWGCompat;
import mod.crend.dynamiccrosshair.compat.tags.IndustrialRevolutionCompat;
import mod.crend.dynamiccrosshair.compat.tags.KibeCompat;
import mod.crend.dynamiccrosshair.compat.tags.MeadowCompat;
import mod.crend.dynamiccrosshair.compat.tags.MultiBedsCompat;
import mod.crend.dynamiccrosshair.compat.tags.NumismaticOverhaulCompat;
import mod.crend.dynamiccrosshair.compat.tags.PatchouliCompat;
import mod.crend.dynamiccrosshair.compat.tags.ThingsCompat;
import mod.crend.dynamiccrosshair.compat.tags.VineryCompat;
import mod.crend.dynamiccrosshairapi.registry.DynamicCrosshairBlockTags;
import mod.crend.dynamiccrosshairapi.registry.DynamicCrosshairEntityTags;
import mod.crend.dynamiccrosshairapi.registry.DynamicCrosshairItemTags;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DCCTagGenerator implements DataGeneratorEntrypoint {

	final static List<DCCProvider> PROVIDERS = new ArrayList<>();

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();

		PROVIDERS.add(new AdornCompat());
		PROVIDERS.add(new AdventureZCompat());
		PROVIDERS.add(new AnotherFurnitureCompat());
		PROVIDERS.add(new ArchonCompat());
		PROVIDERS.add(new BewitchmentCompat());
		PROVIDERS.add(new EnderChestsCompat());
		PROVIDERS.add(new GalosphereCompat());
		PROVIDERS.add(new HWGCompat());
		PROVIDERS.add(new IndustrialRevolutionCompat());
		PROVIDERS.add(new KibeCompat());
		PROVIDERS.add(new MeadowCompat());
		PROVIDERS.add(new MultiBedsCompat());
		PROVIDERS.add(new NumismaticOverhaulCompat());
		PROVIDERS.add(new PatchouliCompat());
		PROVIDERS.add(new ThingsCompat());
		PROVIDERS.add(new VineryCompat());

		pack.addProvider(ItemTagGenerator::new);
		pack.addProvider(BlockTagGenerator::new);
		pack.addProvider(EntityTypeTagGenerator::new);
	}

	public static class ItemTagGenerator extends FabricTagProvider.ItemTagProvider {
		public ItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
			super(output, completableFuture);
		}

		@Override
		protected void configure(RegistryWrapper.WrapperLookup arg) {
			FabricTagProvider<Item>.FabricTagBuilder builderAlwaysUsable = getOrCreateTagBuilder(DynamicCrosshairItemTags.ALWAYS_USABLE);
			FabricTagProvider<Item>.FabricTagBuilder builderAlwaysUsableOnBlock = getOrCreateTagBuilder(DynamicCrosshairItemTags.ALWAYS_USABLE_ON_BLOCK);
			FabricTagProvider<Item>.FabricTagBuilder builderAlwaysUsableOnEntity = getOrCreateTagBuilder(DynamicCrosshairItemTags.ALWAYS_USABLE_ON_ENTITY);
			FabricTagProvider<Item>.FabricTagBuilder builderAlwaysUsableOnMiss = getOrCreateTagBuilder(DynamicCrosshairItemTags.ALWAYS_USABLE_ON_MISS);
			FabricTagProvider<Item>.FabricTagBuilder builderThrowable = getOrCreateTagBuilder(DynamicCrosshairItemTags.THROWABLES);
			for (DCCProvider provider : PROVIDERS) {
				provider.itemsAlwaysUsable(builderAlwaysUsable);
				provider.itemsAlwaysUsableOnBlock(builderAlwaysUsableOnBlock);
				provider.itemsAlwaysUsableOnEntity(builderAlwaysUsableOnEntity);
				provider.itemsAlwaysUsableOnMiss(builderAlwaysUsableOnMiss);
				provider.itemsThrowable(builderThrowable);
			}
		}
	}

	public static class BlockTagGenerator extends FabricTagProvider.BlockTagProvider {
		public BlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
			super(output, completableFuture);
		}

		@Override
		protected void configure(RegistryWrapper.WrapperLookup arg) {
			FabricTagProvider<Block>.FabricTagBuilder builderAlwaysInteractable = getOrCreateTagBuilder(DynamicCrosshairBlockTags.ALWAYS_INTERACTABLE);
			FabricTagProvider<Block>.FabricTagBuilder builderAlwaysInteractableInCreativeMode = getOrCreateTagBuilder(DynamicCrosshairBlockTags.ALWAYS_INTERACTABLE_IN_CREATIVE_MODE);
			for (DCCProvider provider : PROVIDERS) {
				provider.blocksAlwaysInteractable(builderAlwaysInteractable);
				provider.blocksAlwaysInteractableInCreativeMode(builderAlwaysInteractableInCreativeMode);
			}
		}
	}

	public static class EntityTypeTagGenerator extends FabricTagProvider.EntityTypeTagProvider {
		public EntityTypeTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
			super(output, completableFuture);
		}

		@Override
		protected void configure(RegistryWrapper.WrapperLookup arg) {
			FabricTagProvider<EntityType<?>>.FabricTagBuilder builderAlwaysInteractable = getOrCreateTagBuilder(DynamicCrosshairEntityTags.ALWAYS_INTERACTABLE);
			for (DCCProvider provider : PROVIDERS) {
				provider.entitiesAlwaysInteractable(builderAlwaysInteractable);
			}
		}
	}
}
