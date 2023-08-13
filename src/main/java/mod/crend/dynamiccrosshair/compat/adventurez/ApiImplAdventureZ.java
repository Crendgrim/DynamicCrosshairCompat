package mod.crend.dynamiccrosshair.compat.adventurez;

import mod.crend.dynamiccrosshair.DynamicCrosshair;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.ItemCategory;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.config.UsableCrosshairPolicy;
import net.adventurez.block.StoneHolderBlock;
import net.adventurez.block.entity.StoneHolderEntity;
import net.adventurez.entity.EnderWhaleEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.TagInit;
import net.adventurez.item.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ApiImplAdventureZ implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "adventurez";
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof StoneHolderBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();

		if (block instanceof StoneHolderBlock) {
			if (context.getBlockEntity() instanceof StoneHolderEntity blockEntity) {
				ItemStack blockStack = blockEntity.getStack(0);
				if (!blockStack.isEmpty()) {
					return Crosshair.INTERACTABLE;
				} else if ((context.getItemStack().isIn(TagInit.HOLDER_ITEMS) || ConfigInit.CONFIG.allow_all_items_on_holder) && context.world.getBlockState(context.getBlockPos().up()).isAir()) {
					return Crosshair.USABLE;
				}
			}
		}

		return null;
	}

	@Override
	public ItemCategory getItemCategory(ItemStack itemStack) {
		Item item = itemStack.getItem();
		if (item instanceof GildedStoneItem || item instanceof PrimeEyeItem) {
			return ItemCategory.THROWABLE;
		}
		if (item instanceof StoneGolemArm) {
			return ItemCategory.RANGED_WEAPON;
		}
		return DynamicCrosshairApi.super.getItemCategory(itemStack);
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof EnderFluteItem
				|| item instanceof HandbookItem
				|| item instanceof SourceStoneItem;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof StoneGolemHeartItem;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		ItemStack handItemStack = context.getItemStack();
		Item item = handItemStack.getItem();

		if (context.includeUsableItem()) {
			if (item instanceof StoneGolemHeartItem) {
				if (context.player.isSneaking()) {
					return Crosshair.USABLE;
				}
			}
		}

		if (context.includeThrowable()) {
			if (item instanceof GildedStoneItem) {
				if (!context.player.isSneaking()) {
					return Crosshair.THROWABLE;
				}
			}
			if (item instanceof PrimeEyeItem) {
				if (handItemStack.getDamage() <= handItemStack.getMaxDamage() - 1) {
					return Crosshair.THROWABLE;
				}
			}
		}

		if (context.includeRangedWeapon()) {
			if (item instanceof StoneGolemArm) {
				if (DynamicCrosshair.config.dynamicCrosshairHoldingRangedWeapon() == UsableCrosshairPolicy.Always) {
					return Crosshair.RANGED_WEAPON;
				}

				if (context.isRangedWeaponCharged(30)) {
					return Crosshair.RANGED_WEAPON;
				}
				return Crosshair.REGULAR;
			}

		}

		return null;
	}

	@Override
	public boolean isInteractableEntity(Entity entity) {
		return entity instanceof EnderWhaleEntity;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();

		if (entity instanceof EnderWhaleEntity enderWhale && !context.player.shouldCancelInteraction()) {
			Item item = context.getItem();
			if ((item == Items.CHORUS_FRUIT || item == Items.POPPED_CHORUS_FRUIT) && enderWhale.getMaxHealth() - enderWhale.getHealth() > 0.1F){
				return Crosshair.USABLE;
			}

			if (!enderWhale.hasPassengers() || enderWhale.getPassengerList().size() < 2) {
				return Crosshair.INTERACTABLE;
			}
		}

		return null;
	}
}
