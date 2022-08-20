package mod.crend.dynamiccrosshair.compat.farmersdelight;

import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import com.nhoryzon.mc.farmersdelight.advancement.CuttingBoardTrigger;
import com.nhoryzon.mc.farmersdelight.block.*;
import com.nhoryzon.mc.farmersdelight.entity.block.CookingPotBlockEntity;
import com.nhoryzon.mc.farmersdelight.entity.block.CuttingBoardBlockEntity;
import com.nhoryzon.mc.farmersdelight.entity.block.SkilletBlockEntity;
import com.nhoryzon.mc.farmersdelight.entity.block.inventory.ItemHandler;
import com.nhoryzon.mc.farmersdelight.entity.block.inventory.RecipeWrapper;
import com.nhoryzon.mc.farmersdelight.recipe.CuttingBoardRecipe;
import com.nhoryzon.mc.farmersdelight.registry.AdvancementsRegistry;
import com.nhoryzon.mc.farmersdelight.registry.BlocksRegistry;
import com.nhoryzon.mc.farmersdelight.registry.TagsRegistry;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.mixin.farmersdelight.ICuttingBoardBlockEntityMixin;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.BellBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class ApiImplFarmersDelight implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return FarmersDelightMod.MOD_ID;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		Block block = context.getBlock();

		if (block instanceof InventoryBlockWithEntity) {
			return Crosshair.INTERACTABLE;
		}

		if (block instanceof CookingPotBlock) {
			// Note: Farmer's Delight is weird here and checks for sneaking pose rather than sneaking state...
			if (context.getItemStack().isEmpty() && context.player.isInSneakingPose()) {
				return Crosshair.INTERACTABLE;
			}
			if (!context.player.isSneaking()) {
				if (context.getBlockEntity() instanceof CookingPotBlockEntity cookingPotBlockEntity) {
					if (cookingPotBlockEntity.isContainerValid(context.getItemStack())) {
						return Crosshair.USE_ITEM;
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
					return Crosshair.USE_ITEM;
				}
			} else if (!itemHeld.isEmpty()) {
				Optional<CuttingBoardRecipe> matchingRecipe = ((ICuttingBoardBlockEntityMixin) cuttingBoardBlockEntity).invokeGetMatchingRecipe(new RecipeWrapper((ItemHandler) cuttingBoardBlockEntity.getInventory()), itemHeld, context.player);
				if (matchingRecipe.isPresent()) {
					return Crosshair.USE_ITEM;
				}
			} else if (context.isMainHand()) {
				return Crosshair.INTERACTABLE;
			}
		}

		if (block instanceof FeastBlock feastBlock) {
			int servings = context.getBlockState().get(FeastBlock.SERVINGS);
			if (servings == 0) {
				return Crosshair.INTERACTABLE;
			}
			if (servings > 0 && feastBlock.servingItem.hasRecipeRemainder()) {
				Item servingContainerItem = feastBlock.servingItem.getRecipeRemainder();
				if (context.getItem() == servingContainerItem) {
					return Crosshair.USE_ITEM;
				}
			}
		}

		if (block instanceof MushroomColonyBlock) {
			if (context.getBlockState().get(MushroomColonyBlock.AGE) > 0 && context.getItem() instanceof ShearsItem) {
				return Crosshair.USE_ITEM;
			}
		}

		if (block instanceof PieBlock) {
			if (context.getItemStack().isIn(TagsRegistry.KNIVES)) {
				return Crosshair.USE_ITEM;
			}

			if (context.player.canConsume(false)) {
				return Crosshair.USE_ITEM;
			}
		}

		if (block instanceof RichSoilBlock) {
			BlockHitResult hit = (BlockHitResult) context.hitResult;
			if (context.getItem() instanceof HoeItem
					&& context.getBlock() == BlocksRegistry.RICH_SOIL.get()
					&& hit.getSide() != Direction.DOWN
					&& context.world.getBlockState(context.getBlockPos().up()).isAir()) {
				return Crosshair.USE_ITEM;
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

		return null;
	}
}
