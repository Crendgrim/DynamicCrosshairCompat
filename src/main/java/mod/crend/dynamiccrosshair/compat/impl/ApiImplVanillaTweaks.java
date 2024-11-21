package mod.crend.dynamiccrosshair.compat.impl;

import mod.crend.dynamiccrosshairapi.DynamicCrosshairApi;
//? if vanillatweaks {
import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.content.SlimeBucketItem;
import io.github.strikerrocker.vt.content.blocks.pedestal.PedestalBlock;
import io.github.strikerrocker.vt.content.items.craftingpad.CraftingPadItem;
import io.github.strikerrocker.vt.content.items.dynamite.DynamiteItem;
import mod.crend.dynamiccrosshairapi.crosshair.Crosshair;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
//?}

public class ApiImplVanillaTweaks implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "vanillatweaks";
	}

	//? if vanillatweaks {
	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public boolean isAlwaysInteractable(BlockState blockState) {
		return blockState.getBlock() instanceof PedestalBlock;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		if (VanillaTweaksFabric.config.tweaks.shearOffNameTag) {
			if (context.getItem() instanceof ShearsItem && context.getEntity() instanceof LivingEntity le && le.hasCustomName()) {
				return new Crosshair(InteractionType.USE_ITEM_ON_ENTITY);
			}
		}
		return null;
	}

	@Override
	public boolean isAlwaysUsable(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (item instanceof SlimeBucketItem || item instanceof DynamiteItem || item instanceof CraftingPadItem);
	}
	//?}
}
