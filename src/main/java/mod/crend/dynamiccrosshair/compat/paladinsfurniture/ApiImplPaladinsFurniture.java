package mod.crend.dynamiccrosshair.compat.paladinsfurniture;

import com.unlikepaladin.pfm.PaladinFurnitureMod;
import com.unlikepaladin.pfm.blocks.*;
import com.unlikepaladin.pfm.blocks.behavior.BathtubBehavior;
import com.unlikepaladin.pfm.blocks.blockentities.BathtubBlockEntity;
import com.unlikepaladin.pfm.blocks.blockentities.PFMToasterBlockEntity;
import com.unlikepaladin.pfm.blocks.blockentities.PlateBlockEntity;
import com.unlikepaladin.pfm.blocks.blockentities.StovetopBlockEntity;
import com.unlikepaladin.pfm.entity.ChairEntity;
import com.unlikepaladin.pfm.items.DyeKit;
import com.unlikepaladin.pfm.items.FurnitureGuideBook;
import com.unlikepaladin.pfm.items.LightSwitchItem;
import com.unlikepaladin.pfm.items.ShowerHandleItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.mixin.nostrip.INostripClientMixin;
import mod.crend.dynamiccrosshair.compat.mixin.pfm.BasicBathtubBlockAccessor;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.BedPart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

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
		return (item instanceof DyeKit || item instanceof LightSwitchItem || item instanceof ShowerHandleItem);
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
		if (item instanceof ShowerHandleItem) {
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
				|| block instanceof BasicBathtubBlock
				|| block instanceof BasicLampBlock
				|| block instanceof BasicShowerHandleBlock
				|| block instanceof CutleryBlock
				|| block instanceof KitchenStovetopBlock
				|| block instanceof PFMToasterBlock
				|| block instanceof PlateBlock
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

		if (block instanceof PowerableBlock && context.getItem() instanceof LightSwitchItem) {
			if (block instanceof PendantBlock) {
				if (!blockState.get(PendantBlock.UP)) {
					return Crosshair.USABLE;
				}
			} else {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof BasicLampBlock) {
			if (!blockState.get(PowerableBlock.POWERLOCKED)) {
				return Crosshair.INTERACTABLE;
			}
		}

		if (block instanceof BasicShowerHeadBlock) {
			if (context.getItem() instanceof ShowerHandleItem) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof BasicBathtubBlock bathtub) {
			ItemStack itemStack = context.getItemStack();
			BathtubBehavior sinkBehavior = ((BasicBathtubBlockAccessor) bathtub).getBehaviorMap().get(itemStack.getItem());
			if (sinkBehavior != null && itemStack.getItem() != Items.AIR) {
				return Crosshair.USABLE;
			} else if (blockState.get(BasicBathtubBlock.LEVEL_8) > 0 && context.player.isSneaking() && itemStack.isEmpty()) {
				return Crosshair.USABLE;
			} else {
				return Crosshair.INTERACTABLE;
			}
		}

		if (block instanceof CutleryBlock) {
			ItemStack itemStack = context.getItemStack();
			if (itemStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof PlateBlock) {
				return Crosshair.HOLDING_BLOCK;
			}
		}
		if (block instanceof PlateBlock) {
			ItemStack itemStack = context.getItemStack();
			if (!blockState.get(PlateBlock.CUTLERY)) {
				if (itemStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof CutleryBlock) {
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

		if (block instanceof PFMToasterBlock && context.getBlockEntity() instanceof PFMToasterBlockEntity blockEntity) {
			if (!context.player.isSneaking()) {
				if (!blockEntity.isToasting()) {
					ItemStack itemStack = context.getItemStack();
					if (!itemStack.isEmpty() && !PFMToasterBlock.isSandwich(itemStack)) {
						if (blockEntity.getStack(0).isEmpty() || blockEntity.getStack(1).isEmpty()) {
							return Crosshair.USABLE;
						}
					}
					return Crosshair.INTERACTABLE;
				}
			} else {
				return Crosshair.INTERACTABLE;
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
