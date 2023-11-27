package mod.crend.dynamiccrosshair.compat.paladinsfurniture;

import com.unlikepaladin.pfm.PaladinFurnitureMod;
import com.unlikepaladin.pfm.blocks.*;
import com.unlikepaladin.pfm.blocks.blockentities.PlateBlockEntity;
import com.unlikepaladin.pfm.blocks.blockentities.StovetopBlockEntity;
import com.unlikepaladin.pfm.entity.ChairEntity;
import com.unlikepaladin.pfm.items.DyeKit;
import com.unlikepaladin.pfm.items.FurnitureGuideBook;
import com.unlikepaladin.pfm.items.LightSwitchItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.Box;

import java.util.List;

public class ApiImplPaladinsFurniture implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return PaladinFurnitureMod.MOD_ID;
	}

	@Override
	public boolean forceInvalidate(CrosshairContext context) {
		return (context.isWithBlock() && context.getBlock() instanceof PlateBlock);
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof FurnitureGuideBook;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (item instanceof DyeKit || item instanceof LightSwitchItem);
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (!context.includeUsableItem()) return null;

		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof DyeKit
				&& context.player.isSneaking()
				&& context.isWithBlock()
				&& context.getBlock() instanceof DyeableFurnitureBlock
		) {
			return Crosshair.USABLE;
		}
		if (item instanceof LightSwitchItem) {
			if (context.player.isSneaking()) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return (block instanceof ClassicNightstandBlock
				|| block instanceof FreezerBlock
				|| block instanceof FridgeBlock
				|| block instanceof KitchenCabinetBlock
				|| block instanceof KitchenDrawerBlock
				|| block instanceof LightSwitchBlock
				|| block instanceof MicrowaveBlock
				|| block instanceof StoveBlock
				|| block instanceof WorkingTableBlock
		);
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return (block instanceof AbstractSittableBlock
				|| block instanceof CutleryBlock
				|| block instanceof PlateBlock
				|| block instanceof KitchenStovetopBlock
		);
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();

		if (block instanceof AbstractSittableBlock) {
			if (!context.player.isSneaking()) {
				List<ChairEntity> active = context.world.getEntitiesByClass(ChairEntity.class, new Box(context.getBlockPos()), Entity::hasPlayerRider);
				if (active.isEmpty()) {
					return Crosshair.INTERACTABLE;
				}
			}
		}

		if (block instanceof CutleryBlock) {
			ItemStack itemStack = context.getItemStack();
			Block handBlock = Registries.BLOCK.get(Registries.ITEM.getId(itemStack.getItem()));
			if (handBlock instanceof PlateBlock) {
				return Crosshair.HOLDING_BLOCK;
			}
		}
		if (block instanceof PlateBlock) {
			ItemStack itemStack = context.getItemStack();
			if (!blockState.get(PlateBlock.CUTLERY)) {
				Block handBlock = Registries.BLOCK.get(Registries.ITEM.getId(itemStack.getItem()));
				if (handBlock instanceof CutleryBlock) {
					return Crosshair.HOLDING_BLOCK;
				}
			}
			if (context.getBlockEntity() instanceof PlateBlockEntity plateBlockEntity) {
				if (itemStack.isFood()) {
					if (plateBlockEntity.getItemInPlate().isEmpty()) {
						return Crosshair.USABLE;
					}
				} else if (!plateBlockEntity.getItemInPlate().isEmpty()) {
					if (context.player.isSneaking()) {
						return Crosshair.INTERACTABLE;
					} else {
						return Crosshair.USABLE;
					}
				}
			}
		}
		if (block instanceof KitchenStovetopBlock && context.getBlockEntity() instanceof StovetopBlockEntity stovetopBlockEntity) {
			if (stovetopBlockEntity.getRecipeFor(context.getItemStack()).isPresent()) {
				return Crosshair.USABLE;
			}
			for (ItemStack stack : stovetopBlockEntity.getItemsBeingCooked()) {
				if (!stack.isEmpty() && context.world.getRecipeManager().getFirstMatch(RecipeType.CAMPFIRE_COOKING, new SimpleInventory(stack), context.world).isEmpty()) {
					return Crosshair.INTERACTABLE;
				}
			}
		}

		return null;
	}
}
