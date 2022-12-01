package mod.crend.dynamiccrosshair.compat.paradiselost;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.id.paradiselost.ParadiseLost;
import net.id.paradiselost.blocks.blockentity.AmbrosiumCampfireBlockEntity;
import net.id.paradiselost.blocks.blockentity.FoodBowlBlockEntity;
import net.id.paradiselost.blocks.blockentity.IncubatorBlockEntity;
import net.id.paradiselost.blocks.dungeon.DungeonSwitchBlock;
import net.id.paradiselost.blocks.mechanical.AmbrosiumCampfireBlock;
import net.id.paradiselost.blocks.mechanical.FoodBowlBlock;
import net.id.paradiselost.blocks.mechanical.FourBiteCakeBlock;
import net.id.paradiselost.blocks.mechanical.IncubatorBlock;
import net.id.paradiselost.blocks.natural.tree.FruitingLeavesBlock;
import net.id.paradiselost.entities.hostile.AechorPlantEntity;
import net.id.paradiselost.entities.hostile.swet.SwetEntity;
import net.id.paradiselost.entities.passive.AerbunnyEntity;
import net.id.paradiselost.entities.passive.moa.MoaEntity;
import net.id.paradiselost.items.ParadiseLostItems;
import net.id.paradiselost.items.misc.BookOfLoreItem;
import net.id.paradiselost.items.misc.HealingStoneItem;
import net.id.paradiselost.items.misc.ParadiseLostPortalItem;
import net.id.paradiselost.items.tools.bloodstone.BloodstoneItem;
import net.id.paradiselost.tag.ParadiseLostItemTags;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ApiImplParadiseLost implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return ParadiseLost.MOD_ID;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof DungeonSwitchBlock;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof AmbrosiumCampfireBlock
				|| block instanceof FoodBowlBlock
				|| block instanceof FourBiteCakeBlock
				|| block instanceof IncubatorBlock
				|| block instanceof FruitingLeavesBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack itemStack = context.getItemStack();

		if (block instanceof AmbrosiumCampfireBlock && context.getBlockEntity() instanceof AmbrosiumCampfireBlockEntity campfire) {
			if (campfire.getRecipeFor(itemStack).isPresent()) {
				return Crosshair.USABLE;
			}
		}
		if (block instanceof FoodBowlBlock && !context.player.isSneaking() && context.getBlockEntity() instanceof FoodBowlBlockEntity foodBowl) {
			ItemStack storedFood = foodBowl.getItems().get(0);
			if (!itemStack.isEmpty() && (storedFood.isEmpty() || itemStack.isItemEqual(storedFood))) {
				Item food = itemStack.getItem();
				if (food.isFood() && food.getFoodComponent().isMeat()) {
					return Crosshair.USABLE;
				}
			} else if (!storedFood.isEmpty()) {
				return Crosshair.INTERACTABLE;
			}
		}
		if (block instanceof FourBiteCakeBlock && context.player.canConsume(false)) {
			return Crosshair.USABLE;
		}
		if (block instanceof IncubatorBlock && !context.player.isSneaking()) {
			if (!itemStack.isEmpty()) {
				return Crosshair.USABLE;
			} else if (context.getBlockEntity() instanceof IncubatorBlockEntity incubator && !incubator.getItems().get(0).isEmpty()) {
				return Crosshair.INTERACTABLE;
			}
		}
		if (block instanceof FruitingLeavesBlock && blockState.get(FruitingLeavesBlock.GROWTH) > 0) {
			return Crosshair.INTERACTABLE;
		}

		return null;
	}

	@Override
	public boolean isInteractableEntity(Entity entity) {
		return entity instanceof SwetEntity
				|| entity instanceof AechorPlantEntity
				|| entity instanceof MoaEntity
				|| entity instanceof AerbunnyEntity;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (entity instanceof SwetEntity) {
			if (itemStack.isIn(ParadiseLostItemTags.GROWS_SWETS)) {
				return Crosshair.USABLE;
			}
		}
		if (entity instanceof AechorPlantEntity) {
			if (item == ParadiseLostItems.SKYROOT_BUCKET && !context.player.getAbilities().creativeMode) {
				return Crosshair.USABLE;
			}
		}

		if (entity instanceof MoaEntity moa && !(item instanceof BloodstoneItem)) {
			if (moa.getGenes().isTamed()) {
				if (context.player.isSneaking()) {
					return Crosshair.INTERACTABLE;
				}

				if (moa.isSaddled()) {
					if (itemStack.isEmpty()) {
						if (moa.getPassengerList().isEmpty()) {
							return Crosshair.INTERACTABLE;
						}
					}
				} else if (moa.canBeSaddled() && item == Items.SADDLE) {
					return Crosshair.USABLE;
				}

				if (item.isFood() && item.getFoodComponent().isMeat()) {
					return Crosshair.USABLE;
				}

				if (!moa.hasChest() && item instanceof BlockItem blockItem) {
					if (blockItem.getBlock() instanceof AbstractChestBlock) {
						return Crosshair.USABLE;
					}
				}
			} else if (item != ParadiseLostItems.ORANGE && itemStack.isIn(ParadiseLostItemTags.MOA_TEMPTABLES)) {
				return Crosshair.USABLE;
			}
		}
		if (entity instanceof AerbunnyEntity) {
			if (itemStack.isEmpty()) {
				return Crosshair.INTERACTABLE;
			}
		}

		return null;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof BookOfLoreItem
				|| item instanceof HealingStoneItem;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof ParadiseLostPortalItem;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();
		if (context.isWithBlock()) {
			if (item instanceof ParadiseLostPortalItem) {
				return Crosshair.USABLE;
			}
		}
		return null;
	}
}
