package mod.crend.dynamiccrosshair.compat.vanillatweaks;

import io.github.strikerrocker.vt.VanillaTweaks;
import io.github.strikerrocker.vt.VanillaTweaksFabric;
import io.github.strikerrocker.vt.content.SlimeBucketItem;
import io.github.strikerrocker.vt.content.blocks.pedestal.PedestalBlock;
import io.github.strikerrocker.vt.content.items.craftingpad.CraftingPadItem;
import io.github.strikerrocker.vt.content.items.dynamite.DynamiteItem;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.SignBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;

public class ApiImplVanillaTweaks implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return VanillaTweaks.MOD_ID;
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		Block block = context.getBlock();
		if (block instanceof PedestalBlock) {
			return Crosshair.INTERACTABLE;
		}

		if (VanillaTweaksFabric.config.tweaks.enableSignEditing && block instanceof SignBlock && context.player.isSneaking()) {
			return Crosshair.INTERACTABLE;
		}

		return null;
	}

	@Override
	public Crosshair checkEntity(CrosshairContext context) {
		if (VanillaTweaksFabric.config.tweaks.shearOffNameTag) {
			if (context.getItem() instanceof ShearsItem && context.getEntity() instanceof LivingEntity le && le.hasCustomName()) {
				return Crosshair.USE_ITEM;
			}
		}
		return null;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (item instanceof SlimeBucketItem || item instanceof DynamiteItem || item instanceof CraftingPadItem);
	}
}
