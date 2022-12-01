package mod.crend.dynamiccrosshair.compat.soulsweapons;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.soulsweaponry.blocks.AltarBlock;
import net.soulsweaponry.blocks.BlackstonePedestal;
import net.soulsweaponry.items.DragonStaff;
import net.soulsweaponry.items.Nightfall;
import net.soulsweaponry.items.SoulReaper;
import net.soulsweaponry.items.WitheredWabbajack;
import net.soulsweaponry.registry.ItemRegistry;

public class SoulsWeaponryHandler {

	public static boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof AltarBlock || block instanceof BlackstonePedestal;
	}

	public static Crosshair computeFromBlock(CrosshairContext context) {
		Block block = context.getBlock();
		ItemStack itemStack = context.getItemStack();

		if (block instanceof AltarBlock && itemStack.isOf(ItemRegistry.LOST_SOUL)) {
			return Crosshair.USABLE;
		}
		if (block instanceof BlackstonePedestal && itemStack.isOf(ItemRegistry.SHARD_OF_UNCERTAINTY)) {
			return Crosshair.USABLE;
		}
		return null;
	}

	public static boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof DragonStaff
				|| item instanceof Nightfall
				|| item instanceof WitheredWabbajack
				|| itemStack.isOf(ItemRegistry.MOONSTONE_RING);
	}

	public static boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof SoulReaper;
	}

	public static Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof SoulReaper) {
			if (itemStack.hasNbt() && itemStack.getNbt().contains("kills")) {
				int power = itemStack.getNbt().getInt("kills");
				if (power >= 3) {
					return Crosshair.USABLE;
				}
			}
		}
		return null;
	}

	// Blunderbuss special gun item

}
