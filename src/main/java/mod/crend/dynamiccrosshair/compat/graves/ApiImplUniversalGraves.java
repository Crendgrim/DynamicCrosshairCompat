package mod.crend.dynamiccrosshair.compat.graves;

import eu.pb4.graves.config.ConfigManager;
import eu.pb4.graves.registry.*;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;

public class ApiImplUniversalGraves implements DynamicCrosshairApi {
	@Override
	public String getModId() {
		return "universal-graves";
	}

	@Override
	public String getNamespace() {
		return "universal_graves";
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof AbstractGraveBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();

		if (block instanceof GraveBlock && context.isMainHand()) {
			if (!ConfigManager.getConfig().configData.shiftClickTakesItems || !context.player.isSneaking() && ConfigManager.getConfig().configData.clickGraveToOpenGui) {
				return Crosshair.INTERACTABLE;
			} else {
				return Crosshair.USABLE;
			}
		}
		if (block instanceof VisualGraveBlock) {
			ItemStack itemStack = context.getItemStack();
			if (itemStack.isOf(Items.FEATHER)
					|| itemStack.isOf(Items.PLAYER_HEAD)
					|| itemStack.isOf(Items.MOSS_BLOCK)
					|| itemStack.isOf(Items.SPONGE)
					|| itemStack.isOf(Items.WET_SPONGE)
					|| itemStack.getItem() instanceof ShovelItem
			) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
