package mod.crend.dynamiccrosshair.compat.alkimicraft;

import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.IBlockInteractHandler;
import mod.crend.dynamiccrosshair.api.IUsableItemHandler;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.thenotfish.alkimicraft.AlkimiCraft;
import net.thenotfish.alkimicraft.blocks.*;
import net.thenotfish.alkimicraft.blocks.entities.TipiFireEntity;
import net.thenotfish.alkimicraft.init.BlockInit;
import net.thenotfish.alkimicraft.init.ItemInit;
import net.thenotfish.alkimicraft.init.tags.ItemTagsInit;
import net.thenotfish.alkimicraft.items.CreatureInBag;
import net.thenotfish.alkimicraft.items.FirePlough;
import net.thenotfish.alkimicraft.items.GrassSeeds;

public class ApiImplAlkimiCraft implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return AlkimiCraft.MOD_ID;
	}

	@Override
	public IBlockInteractHandler getBlockInteractHandler() {
		return (player, itemStack, blockPos, blockState) -> {
			Item handItem = itemStack.getItem();
			Block block = blockState.getBlock();

			if (block instanceof TipiFire) {
				if (itemStack.isIn(ItemTagsInit.BURNABLE_ITEMS)) {
					return Crosshair.INTERACTABLE;
				}
				TipiFireEntity blockEntity = (TipiFireEntity) MinecraftClient.getInstance().world.getBlockEntity(blockPos);
				if (blockEntity.getRecipeFor(itemStack).isPresent()) {
					return Crosshair.INTERACTABLE;
				}
			}
			if (block instanceof Microscope) {
				return Crosshair.INTERACTABLE;
			}
			if (block instanceof SeaBuckthorn) {
				boolean fullyGrown = blockState.get(SeaBuckthorn.AGE) == 5;
				if (fullyGrown) {
					return Crosshair.INTERACTABLE;
				}
			}
			if (block instanceof AbstractWoodenBarrel barrel && !player.shouldCancelInteraction()) {
				if (handItem == Items.WATER_BUCKET || handItem == ItemInit.WATER_WOODEN_BUCKET) {
					if (block instanceof WoodenBarrel) {
						return Crosshair.USE_ITEM.withFlag(Crosshair.Flag.FixedAll);
					}
				} else if (handItem instanceof PotionItem && PotionUtil.getPotion(itemStack) == Potions.WATER) {
					if (!barrel.isFull(blockState)) {
						return Crosshair.USE_ITEM;
					}
				} else {
					if (block instanceof LeveledBarrel) {
						if (handItem instanceof GlassBottleItem) {
							return Crosshair.USE_ITEM;
						}
						if (barrel.isFull(blockState)) {
							if (handItem == Items.BUCKET || handItem == ItemInit.WOODEN_BUCKET) {
								return Crosshair.USE_ITEM;
							}
						}
					}
					if (handItem instanceof BlockItem bi && bi.getBlock() instanceof WoodenBarrel) {
						return Crosshair.INTERACTABLE;
					}
				}
			}

			return null;
		};
	}

	IUsableItemHandler usableItemHandler = new AlkimiCraftUsableItemHandler();

	@Override
	public IUsableItemHandler getUsableItemHandler() {
		return usableItemHandler;
	}

	private static class AlkimiCraftUsableItemHandler implements IUsableItemHandler {
		@Override
		public boolean isUsableItem(ItemStack itemStack) {
			Item handItem = itemStack.getItem();
			return (handItem instanceof CreatureInBag
					|| handItem instanceof GrassSeeds
					|| handItem instanceof FirePlough);
		}

		@Override
		public Crosshair checkUsableItemOnBlock(ClientPlayerEntity player, ItemStack itemStack, BlockPos blockPos, BlockState blockState) {
			Item handItem = itemStack.getItem();
			Block block = blockState.getBlock();

			if (handItem instanceof CreatureInBag) {
				return Crosshair.USE_ITEM;
			}
			if (handItem instanceof GrassSeeds) {
				if (block == Blocks.DIRT || block == BlockInit.BARREN_LOAM) {
					return Crosshair.USE_ITEM;
				}
			}
			if (handItem instanceof FirePlough) {
				if (CampfireBlock.canBeLit(blockState) || CandleBlock.canBeLit(blockState) || CandleCakeBlock.canBeLit(blockState)) {
					return Crosshair.USE_ITEM;
				} else {
					BlockHitResult hitResult = (BlockHitResult) MinecraftClient.getInstance().crosshairTarget;
					if (AbstractFireBlock.canPlaceAt(MinecraftClient.getInstance().world, blockPos.offset(hitResult.getSide()), hitResult.getSide())) {
						return Crosshair.USE_ITEM;
					}
				}
			}

			if (block instanceof BlockIronCobblestone && itemStack.isIn(ItemTags.CLUSTER_MAX_HARVESTABLES)) {
				return Crosshair.USE_ITEM;
			}

			return null;
		}
	}
}
