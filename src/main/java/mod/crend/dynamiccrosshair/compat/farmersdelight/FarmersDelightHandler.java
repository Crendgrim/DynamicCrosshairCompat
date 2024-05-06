package mod.crend.dynamiccrosshair.compat.farmersdelight;

import io.github.fabricators_of_create.porting_lib.tool.ToolActions;
import io.github.fabricators_of_create.porting_lib.transfer.item.RecipeWrapper;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.compat.mixin.farmersdelight.CuttingBoardBlockEntityMixin;
import mod.crend.dynamiccrosshair.compat.mixin.farmersdelight.SkilletBlockEntityMixin;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.block.BasketBlock;
import vectorwing.farmersdelight.common.block.CabinetBlock;
import vectorwing.farmersdelight.common.block.CookingPotBlock;
import vectorwing.farmersdelight.common.block.CuttingBoardBlock;
import vectorwing.farmersdelight.common.block.FeastBlock;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.block.RopeBlock;
import vectorwing.farmersdelight.common.block.SkilletBlock;
import vectorwing.farmersdelight.common.block.StoveBlock;
import vectorwing.farmersdelight.common.block.TomatoVineBlock;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;
import vectorwing.farmersdelight.common.block.entity.StoveBlockEntity;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.Optional;

public class FarmersDelightHandler {

	public static boolean isAlwaysInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return (   block instanceof BasketBlock
				|| block instanceof CabinetBlock
		);
	}

	public static boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return (   block instanceof CookingPotBlock
				|| block instanceof CuttingBoardBlock
				|| block instanceof FeastBlock
				|| block instanceof RopeBlock
				|| block instanceof SkilletBlock
		);
	}

	public static Crosshair computeFromBlock(CrosshairContext context) {
		Block block = context.getBlock();

		if (block instanceof CookingPotBlock) {
			if (context.getItemStack().isEmpty() && context.player.isSneaking()) {
				return Crosshair.INTERACTABLE;
			}
			if (context.getBlockEntity() instanceof CookingPotBlockEntity cookingPotBlockEntity) {
				if (cookingPotBlockEntity.isContainerValid(context.getItemStack()) && !cookingPotBlockEntity.getMeal().isEmpty()) {
					return Crosshair.USABLE;
				}
			}
			return Crosshair.INTERACTABLE;
		}

		if (block instanceof CuttingBoardBlock && context.getBlockEntity() instanceof CuttingBoardBlockEntity cuttingBoardBlockEntity) {
			ItemStack heldStack = context.getItemStack();
			ItemStack offhandStack = context.getItemStack();
			if (cuttingBoardBlockEntity.isEmpty()) {
				// Logic copied from mod
				if (!offhandStack.isEmpty()) {
					if (context.isMainHand() && !offhandStack.isIn(ModTags.OFFHAND_EQUIPMENT) && !(heldStack.getItem() instanceof BlockItem)) {
						return Crosshair.NONE;
					}

					if (context.isOffHand() && offhandStack.isIn(ModTags.OFFHAND_EQUIPMENT)) {
						return Crosshair.NONE;
					}
				}

				if (heldStack.isEmpty()) {
					return Crosshair.NONE;
				}

				if (cuttingBoardBlockEntity.isEmpty() && !heldStack.isEmpty()) {
					return Crosshair.USABLE;
				}
			} else {
				if (!heldStack.isEmpty()) {
					if (!cuttingBoardBlockEntity.isItemCarvingBoard()) {
						Optional<CuttingBoardRecipe> matchingRecipe = ((CuttingBoardBlockEntityMixin) cuttingBoardBlockEntity).invokeGetMatchingRecipe(new RecipeWrapper(cuttingBoardBlockEntity.getInventory()), heldStack, context.player);
						if (matchingRecipe.isPresent()) {
							return Crosshair.USABLE;
						}
					}
				} else if (context.isMainHand()) {
					return Crosshair.INTERACTABLE;
				}
			}
		}

		if (block instanceof FeastBlock feastBlock) {
			int servings = context.getBlockState().get(feastBlock.getServingsProperty());
			if (servings == 0) {
				return Crosshair.INTERACTABLE;
			}
			ItemStack serving = feastBlock.getServingItem(context.getBlockState());
			ItemStack heldItem = context.getItemStack();
			if (!serving.getRecipeRemainder().isEmpty() || ItemStack.areItemsEqual(heldItem, serving.getRecipeRemainder())) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof MushroomColonyBlock) {
			if (context.getBlockState().get(MushroomColonyBlock.COLONY_AGE) > 0 && context.getItem() instanceof ShearsItem) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof PieBlock) {
			if (context.getItemStack().isIn(ModTags.KNIVES)) {
				return Crosshair.USABLE;
			}

			if (context.player.canConsume(false)) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof RopeBlock) {
			if (context.getItemStack().isEmpty()) {
				BlockPos pos = context.getBlockPos();
				BlockPos.Mutable mutablePos;
				int minBuildHeight;
				BlockState blockState;
				if (Configuration.ENABLE_ROPE_REELING.get() && context.player.shouldCancelInteraction()) {
					if (context.player.getAbilities().allowModifyWorld) {
						mutablePos = pos.mutableCopy().move(Direction.DOWN);
						minBuildHeight = context.world.getBottomY();

						while (mutablePos.getY() >= minBuildHeight) {
							blockState = context.world.getBlockState(mutablePos);
							if (!blockState.isOf(block)) {
								return Crosshair.INTERACTABLE;
							}

							mutablePos.move(Direction.DOWN);
						}
					}
				} else {
					mutablePos = pos.mutableCopy().move(Direction.UP);

					for (minBuildHeight = 0; minBuildHeight < 24; ++minBuildHeight) {
						blockState = context.world.getBlockState(mutablePos);
						Block blockAbove = blockState.getBlock();
						if (blockAbove == Blocks.BELL) {
							return Crosshair.INTERACTABLE;
						}

						if (blockAbove != ModBlocks.ROPE.get()) {
							return Crosshair.NONE;
						}

						mutablePos.move(Direction.UP);
					}
				}
			}
		}

		if (block instanceof SkilletBlock && context.getBlockEntity() instanceof SkilletBlockEntity skilletEntity) {
			ItemStack heldStack = context.getItemStack();
			if (heldStack.isIn(ItemTags.SHOVELS) && skilletEntity.isCooking()) {
				if ((float)skilletEntity.lastFlippedTime + 20.0F < (float)context.world.getTime()) {
					return Crosshair.USABLE;
				}
				return Crosshair.NONE;
			}

			if (heldStack.isEmpty()) {
				return Crosshair.INTERACTABLE;
			}

			Optional<CampfireCookingRecipe> recipe = ((SkilletBlockEntityMixin) skilletEntity).invokeGetMatchingRecipe(new SimpleInventory(heldStack));
			if (recipe.isPresent()) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof StoveBlock) {
			BlockState state = context.getBlockState();
			ItemStack heldStack = context.getItemStack();
			Item heldItem = heldStack.getItem();
			if (state.get(StoveBlock.LIT)) {
				if (heldStack.canPerformAction(ToolActions.SHOVEL_DIG)) {
					return Crosshair.USABLE;
				}

				if (heldItem == Items.WATER_BUCKET) {
					return Crosshair.USABLE;
				}
			} else {
				if (heldItem instanceof FlintAndSteelItem) {
					return Crosshair.USABLE;
				}

				if (heldItem instanceof FireChargeItem) {
					return Crosshair.USABLE;
				}
			}

			if (context.getBlockEntity() instanceof StoveBlockEntity stoveEntity) {
				int stoveSlot = stoveEntity.getNextEmptySlot();
				if (stoveSlot < 0 || stoveEntity.isStoveBlockedAbove()) {
					return Crosshair.NONE;
				}

				Optional<CampfireCookingRecipe> recipe = stoveEntity.getMatchingRecipe(new SimpleInventory(heldStack), stoveSlot);
				if (recipe.isPresent()) {
					return Crosshair.USABLE;
				}
			}
		}

		if (block instanceof TomatoVineBlock tomato) {
			if (tomato.isMature(context.getBlockState())) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
