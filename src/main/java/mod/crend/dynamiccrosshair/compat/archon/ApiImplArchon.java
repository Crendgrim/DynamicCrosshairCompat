package mod.crend.dynamiccrosshair.compat.archon;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import safro.archon.Archon;
import safro.archon.api.Spell;
import safro.archon.block.ScriptureTableBlock;
import safro.archon.block.SummoningPedestalBlock;
import safro.archon.block.entity.SummoningPedestalBlockEntity;
import safro.archon.entity.PrimeSkeltEntity;
import safro.archon.entity.SkeltEntity;
import safro.archon.item.*;
import safro.archon.recipe.ChannelingRecipe;
import safro.archon.registry.ItemRegistry;
import safro.archon.registry.RecipeRegistry;
import safro.archon.util.ArchonUtil;

public class ApiImplArchon implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Archon.MODID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof ExperiencePouchItem
				|| item instanceof GrimoireItem
				|| item instanceof LightningBottleItem
				|| item instanceof TomeItem
				;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof ChannelerItem
				|| item instanceof CombustionChargeItem
				|| item instanceof ManaItem
				|| item instanceof ManaWeapon
				|| item instanceof ScrollItem
				|| item instanceof RemovalScrollItem
				;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof ManaItem manaItem) {
			if (ArchonUtil.canRemoveMana(context.player, manaItem.getManaCost())) {
				return Crosshair.USABLE;
			}
		}
		if (item instanceof ManaWeapon manaWeapon) {
			if (ArchonUtil.canRemoveMana(context.player, manaWeapon.getManaCost())) {
				return Crosshair.USABLE;
			}
		}

		if (item instanceof ScrollItem) {
			if (!ArchonUtil.hasScroll(context.player)) {
				return Crosshair.USABLE;
			}
		}
		if (item instanceof RemovalScrollItem) {
			if (ArchonUtil.hasScroll(context.player)) {
				return Crosshair.USABLE;
			}
		}

		if (item instanceof WandItem wandItem) {
			if (!wandItem.getSpells(context.player).isEmpty()) {
				Spell current = wandItem.getCurrentSpell(itemStack, context.player);
				if (context.player.isSneaking()) {
					return Crosshair.USABLE;
				}

				if (current != null) {
					if (context.isWithBlock()) {
						if (current.isBlockCasted() && ArchonUtil.canRemoveMana(context.player, current.getManaCost())) {
							return Crosshair.USABLE;
						}
					} else {
						if (!current.isBlockCasted() && current.canCast(context.world, context.player, itemStack)) {
							return Crosshair.USABLE;
						}
					}
				}
			}
		}

		if (context.isWithBlock()) {
			BlockState blockState = context.getBlockState();
			Block block = blockState.getBlock();
			if (item instanceof ChannelerItem) {
				ChannelingRecipe recipe = context.world.getRecipeManager()
						.listAllOfType(RecipeRegistry.CHANNELING)
						.stream()
						.filter((entry) -> ChannelingRecipe.isValid(entry, block))
						.findFirst()
						.orElse(null);
				if (recipe != null && ArchonUtil.canRemoveMana(context.player, recipe.getManaCost())) {
					return Crosshair.USABLE;
				}
			}
			if (item instanceof CombustionChargeItem) {
				return Crosshair.USABLE;
			}
		}
		return null;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof ScriptureTableBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		ItemStack itemStack = context.getItemStack();
		if (blockState.getBlock() instanceof SummoningPedestalBlock && context.getBlockEntity() instanceof SummoningPedestalBlockEntity pedestal) {
			if (pedestal.isIdle()) {
				if (context.player.isSneaking()) {
					return Crosshair.INTERACTABLE;
				} else if (!itemStack.isEmpty() && !pedestal.hasItem(itemStack.getItem())) {
					return Crosshair.USABLE;
				} else {
					return Crosshair.INTERACTABLE;
				}
			}
		}
		return null;
	}

	@Override
	public boolean isInteractableEntity(Entity entity) {
		return entity instanceof SkeltEntity;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();
		ItemStack itemStack = context.getItemStack();

		if (entity instanceof SkeltEntity skelt) {
			if (entity instanceof PrimeSkeltEntity && context.player.isSneaking()) {
				if (itemStack.isEmpty()) {
					if (skelt.hasStackEquipped(EquipmentSlot.HEAD)
							|| skelt.hasStackEquipped(EquipmentSlot.CHEST)
							|| skelt.hasStackEquipped(EquipmentSlot.LEGS)
							|| skelt.hasStackEquipped(EquipmentSlot.FEET)
					) {
						return Crosshair.INTERACTABLE;
					}
				} else if (itemStack.getItem() instanceof ArmorItem armor) {
					if (!skelt.hasStackEquipped(armor.getSlotType())) {
						return Crosshair.USABLE;
					}
				}
			}
			if (skelt.isTamed() && skelt.isOwner(context.player) && itemStack.isOf(ItemRegistry.UNDEAD_STAFF)) {
				return Crosshair.INTERACTABLE;
			}
		}
		return null;
	}
}
