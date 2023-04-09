package mod.crend.dynamiccrosshair.compat.goodall;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import tech.thatgravyboat.goodall.Goodall;
import tech.thatgravyboat.goodall.common.entity.*;
import tech.thatgravyboat.goodall.common.item.AnimalCrateBlockItem;
import tech.thatgravyboat.goodall.common.item.EntityBottleItem;

public class ApiImplGoodall implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Goodall.MOD_ID;
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();

		if (context.isWithBlock()) {
			if (itemStack.getItem() instanceof EntityBottleItem) {
				if (!context.player.isSneaking() && EntityBottleItem.isFilled(itemStack)) {
					return Crosshair.USABLE;
				}
			}
		}

		if (context.isWithEntity()) {
			Entity entity = context.getEntity();
			if (itemStack.getItem() instanceof AnimalCrateBlockItem) {
				if ((!itemStack.hasNbt() || !itemStack.getOrCreateNbt().contains("BlockEntityTag")) && entity.getType().isIn(AnimalCrateBlockItem.ALLOWED_ANIMALS)) {
					return Crosshair.USABLE;
				}
			}
		}
		return null;
	}

	@Override
	public boolean isInteractableEntity(Entity entity) {
		return entity instanceof Ant
				|| entity instanceof FennecFox
				|| entity instanceof KiwiEntity
				|| entity instanceof Owl
				|| entity instanceof PelicanEntity
				|| entity instanceof Toucan
				;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();
		ItemStack itemStack = context.getItemStack();

		if (entity instanceof Ant ant) {
			ItemStack antItem = ant.getStackInHand(context.getHand());
			if (itemStack.isFood() && !itemStack.getItem().hasRecipeRemainder()) {
				if (ItemStack.canCombine(itemStack, antItem) && antItem.getCount() < antItem.getMaxCount()) {
					return Crosshair.USABLE;
				} else if (antItem.isEmpty()) {
					return Crosshair.USABLE;
				}
			}
		}

		if (entity instanceof FennecFox fennec) {
			if (fennec.isTamed()) {
				if (fennec.isOwner(context.player)) {
					if (!context.player.isSneaking() || !fennec.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
						return Crosshair.INTERACTABLE;
					}
				}
			} else if (itemStack.isOf(Items.CHICKEN) && fennec.getAttacker() == null && fennec.getTarget() == null) {
				return Crosshair.USABLE;
			}
		}

		if (entity instanceof KiwiEntity kiwi) {
			if (itemStack.isEmpty() && kiwi.isAlive()) {
				return Crosshair.INTERACTABLE;
			}
		}

		if (entity instanceof KrillEntity) {
			if (itemStack.isOf(Items.WATER_BUCKET)) {
				return Crosshair.USABLE;
			}
		}

		if (entity instanceof Owl owl) {
			if (owl.isTamed()) {
				if (owl.isOwner(context.player)) {
					return Crosshair.INTERACTABLE;
				}
			} else if (Owl.FOOD.test(itemStack) && owl.getAttacker() == null && owl.getTarget() == null) {
				return Crosshair.USABLE;
			}
		}

		if (entity instanceof PelicanEntity) {
			if (!itemStack.isEmpty() && itemStack.isOf(Items.TROPICAL_FISH)) {
				return Crosshair.USABLE;
			}
		}

		if (entity instanceof Toucan toucan) {
			if (!toucan.isTamed()) {
				if (itemStack.isOf(Items.APPLE)) {
					return Crosshair.USABLE;
				}
			} else if (toucan.isOwner(context.player)) {
				if (itemStack.isOf(Items.GOLDEN_APPLE) || itemStack.isOf(Items.ENCHANTED_GOLDEN_APPLE)) {
					return Crosshair.USABLE;
				} else {
					return Crosshair.INTERACTABLE;
				}
			}
		}

		if (itemStack.isOf(Items.FEATHER) && entity instanceof PandaEntity) {
			return Crosshair.USABLE;
		}

		if (itemStack.isOf(Items.GLASS_BOTTLE)) {
			if (entity.isAlive() && EntityBottleItem.get(entity.getType()) != null) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
