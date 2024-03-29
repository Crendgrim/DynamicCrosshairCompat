package mod.crend.dynamiccrosshair.compat.farmersdelight;

import com.nhoryzon.mc.farmersdelight.block.*;
import com.nhoryzon.mc.farmersdelight.entity.block.CookingPotBlockEntity;
import com.nhoryzon.mc.farmersdelight.entity.block.CuttingBoardBlockEntity;
import com.nhoryzon.mc.farmersdelight.entity.block.inventory.RecipeWrapper;
import com.nhoryzon.mc.farmersdelight.recipe.CuttingBoardRecipe;
import com.nhoryzon.mc.farmersdelight.registry.BlocksRegistry;
import com.nhoryzon.mc.farmersdelight.registry.TagsRegistry;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.compat.mixin.farmersdelight.ICuttingBoardBlockEntityMixin;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Optional;

public class FarmersDelightHandler {

	public static boolean isAlwaysInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return (   block instanceof InventoryBlockWithEntity
				|| block instanceof SkilletBlock
		);
	}

	public static boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return (   block instanceof CookingPotBlock
				|| block instanceof CuttingBoardBlock
				|| block instanceof FeastBlock
		);
	}

	public static Crosshair computeFromBlock(CrosshairContext context) {
		Block block = context.getBlock();

		if (block instanceof CookingPotBlock) {
			// Note: Farmer's Delight is weird here and checks for sneaking pose rather than sneaking state...
			if (context.getItemStack().isEmpty() && context.player.isInSneakingPose()) {
				return Crosshair.INTERACTABLE;
			}
			if (!context.player.isSneaking()) {
				if (context.getBlockEntity() instanceof CookingPotBlockEntity cookingPotBlockEntity) {
					if (cookingPotBlockEntity.isContainerValid(context.getItemStack())) {
						return Crosshair.USABLE;
					}
				}
				return Crosshair.INTERACTABLE;
			}
		}

		if (block instanceof CuttingBoardBlock && context.getBlockEntity() instanceof CuttingBoardBlockEntity cuttingBoardBlockEntity) {
			ItemStack itemHeld = context.getItemStack();
			if (cuttingBoardBlockEntity.isEmpty() && !itemHeld.isEmpty()) {
				// Logic copied from mod
				if (context.player.getOffHandStack().isEmpty() || !context.isMainHand() || itemHeld.getItem() instanceof BlockItem) {
					return Crosshair.USABLE;
				}
			} else if (!itemHeld.isEmpty()) {
				Optional<CuttingBoardRecipe> matchingRecipe = ((ICuttingBoardBlockEntityMixin) cuttingBoardBlockEntity).invokeGetMatchingRecipe(new RecipeWrapper(cuttingBoardBlockEntity), itemHeld, context.player);
				if (matchingRecipe.isPresent()) {
					return Crosshair.USABLE;
				}
			} else if (context.isMainHand()) {
				return Crosshair.INTERACTABLE;
			}
		}

		if (block instanceof FeastBlock feastBlock) {
			int servings = context.getBlockState().get(feastBlock.getServingsProperty());
			if (servings == 0) {
				return Crosshair.INTERACTABLE;
			}
			ItemStack serving = feastBlock.getServingStack(context.getBlockState());
			ItemStack heldItem = context.getItemStack();
			if (!serving.getItem().hasRecipeRemainder() || heldItem.isOf(serving.getItem().getRecipeRemainder())) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof MushroomColonyBlock) {
			if (context.getBlockState().get(MushroomColonyBlock.AGE) > 0 && context.getItem() instanceof ShearsItem) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof PieBlock) {
			if (context.getItemStack().isIn(TagsRegistry.KNIVES)) {
				return Crosshair.USABLE;
			}

			if (context.player.canConsume(false)) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof RichSoilBlock) {
			if (context.getItem() instanceof HoeItem
					&& context.getBlock() == BlocksRegistry.RICH_SOIL.get()
					&& context.getBlockHitSide() != Direction.DOWN
					&& context.world.getBlockState(context.getBlockPos().up()).isAir()) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof RopeBlock) {
			if (context.getItemStack().isEmpty()) {
				BlockPos.Mutable mutable = context.getBlockPos().mutableCopy().move(Direction.UP);

				for (int i = 0; i < 24; ++i) {
					BlockState blockStateAbove = context.world.getBlockState(mutable);
					Block blockAbove = blockStateAbove.getBlock();
					if (blockAbove == Blocks.BELL) {
						return Crosshair.INTERACTABLE;
					}

					if (blockAbove != BlocksRegistry.ROPE.get()) {
						break;
					}

					mutable.move(Direction.UP);
				}
			}
		}

		if (block instanceof SkilletBlock) {
			return Crosshair.INTERACTABLE;
		}

		if (block instanceof TomatoVineBlock tomato) {
			if (tomato.isMature(context.getBlockState())) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
