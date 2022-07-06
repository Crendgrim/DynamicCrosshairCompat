package mod.crend.dynamiccrosshair.compat.adventurez;

import mod.crend.dynamiccrosshair.DynamicCrosshair;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.config.RangedCrosshairPolicy;
import net.adventurez.block.StoneHolderBlock;
import net.adventurez.block.entity.StoneHolderEntity;
import net.adventurez.init.ConfigInit;
import net.adventurez.init.TagInit;
import net.adventurez.item.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ApiImplAdventureZ implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "adventurez";
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();

		if (block instanceof StoneHolderBlock) {
			if (context.getBlockEntity() instanceof StoneHolderEntity blockEntity) {
				ItemStack blockStack = blockEntity.getStack(0);
				if (!blockStack.isEmpty()) {
					return Crosshair.INTERACTABLE;
				} else if ((context.getItemStack().isIn(TagInit.HOLDER_ITEMS) || ConfigInit.CONFIG.allow_all_items_on_holder) && context.world.getBlockState(context.getBlockPos().up()).isAir()) {
					return Crosshair.USE_ITEM;
				}
			}
		}

		return null;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof SourceStoneItem
				|| item instanceof HandbookItem;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof EnderFluteItem
				|| item instanceof StoneGolemHeartItem;
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		ItemStack handItemStack = context.getItemStack();
		Item item = handItemStack.getItem();

		if (item instanceof EnderFluteItem) {
			if (!context.player.getItemCooldownManager().isCoolingDown(item)) {
				return Crosshair.USE_ITEM;
			}
		}
		if (item instanceof StoneGolemHeartItem) {
			if (context.player.isSneaking()) {
				return Crosshair.USE_ITEM;
			}
		}

		return null;
	}

	@Override
	public Crosshair checkThrowable(CrosshairContext context) {
		ItemStack handItemStack = context.getItemStack();
		Item item = handItemStack.getItem();
		if (item instanceof GildedStoneItem) {
			if (!context.player.isSneaking()) {
				return Crosshair.USE_ITEM;
			}
		}
		if (item instanceof PrimeEyeItem) {
			if (handItemStack.getDamage() <= handItemStack.getMaxDamage() - 1 && !context.player.getItemCooldownManager().isCoolingDown(item)) {
				return Crosshair.THROWABLE;
			}
		}

		return null;
	}

	@Override
	public Crosshair checkRangedWeapon(CrosshairContext context) {
		ItemStack handItemStack = context.getItemStack();
		Item item = handItemStack.getItem();

		if (item instanceof StoneGolemArm) {
			if (DynamicCrosshair.config.dynamicCrosshairHoldingRangedWeapon() == RangedCrosshairPolicy.Always) {
				return Crosshair.RANGED_WEAPON;
			}

			if (context.isActiveItem()) {
				int stoneCounter = item.getMaxUseTime(handItemStack) - context.player.getItemUseTimeLeft();
				if (stoneCounter >= 30) {
					return Crosshair.RANGED_WEAPON;
				}
			}
			return Crosshair.REGULAR;
		}

		return null;
	}

}
