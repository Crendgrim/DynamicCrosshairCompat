package mod.crend.dynamiccrosshair.compat.techreborn;

import ml.pkom.advancedreborn.AdvancedReborn;
import ml.pkom.advancedreborn.blocks.CardboardBox;
import ml.pkom.advancedreborn.blocks.IndustrialTNT;
import ml.pkom.advancedreborn.items.AdvancedBattery;
import ml.pkom.advancedreborn.items.ConfigWrench;
import ml.pkom.advancedreborn.items.Dynamite;
import ml.pkom.advancedreborn.items.FreqTrans;
import ml.pkom.advancedreborn.tile.TeleporterTile;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import reborncore.common.blockentity.MachineBaseBlockEntity;

public class ApiImplAdvancedReborn implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return AdvancedReborn.MOD_ID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return (itemStack.getItem() instanceof Dynamite);
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		if (RebornCoreHandler.isUsableItem(itemStack)) {
			return true;
		}
		Item item = itemStack.getItem();
		return (item instanceof AdvancedBattery
				|| item instanceof ConfigWrench
				|| item instanceof FreqTrans
		);
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		Crosshair crosshair = RebornCoreHandler.checkUsableItem(context);
		if (crosshair != null) return crosshair;

		Item item = context.getItem();
		if (item instanceof AdvancedBattery) {
			if (context.player.isSneaking()) {
				return Crosshair.USE_ITEM;
			}
		}

		if (item instanceof ConfigWrench) {
			if (context.getBlockEntity() instanceof MachineBaseBlockEntity) {
				return Crosshair.USE_ITEM;
			}
		}
		if (item instanceof FreqTrans) {
			if (context.getBlockEntity() instanceof TeleporterTile) {
				return Crosshair.USE_ITEM;
			}
		}

		return null;
	}

	@Override
	public Crosshair checkBlockInteractable(CrosshairContext context) {
		Crosshair crosshair = RebornCoreHandler.checkBlockInteractable(context);
		if (crosshair != null) return crosshair;

		Block block = context.getBlock();
		if (block instanceof IndustrialTNT || block instanceof CardboardBox) {
			return Crosshair.INTERACTABLE;
		}

		return null;
	}

}
