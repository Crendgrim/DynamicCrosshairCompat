package mod.crend.dynamiccrosshair.compat.bewitchment;

import dev.mrsterner.bewitchmentplus.BewitchmentPlus;
import dev.mrsterner.bewitchmentplus.common.block.*;
import dev.mrsterner.bewitchmentplus.common.block.blockentity.GobletBlockEntity;
import dev.mrsterner.bewitchmentplus.common.item.DragonbloodStaffItem;
import dev.mrsterner.bewitchmentplus.common.item.GobletBlockItem;
import dev.mrsterner.bewitchmentplus.common.item.MutandisBrew;
import dev.mrsterner.bewitchmentplus.common.item.MutandisItem;
import dev.mrsterner.bewitchmentplus.common.registry.BWPTags;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.api.ItemCategory;
import mod.crend.dynamiccrosshair.component.Crosshair;
import moriyashiine.bewitchment.common.registry.BWObjects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.*;
import net.minecraft.state.property.Properties;

public class ApiImplBewitchmentPlus implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return BewitchmentPlus.MODID;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof DragonbloodStaffItem
				|| item instanceof MutandisItem;
	}

	@Override
	public ItemCategory getItemCategory(ItemStack itemStack) {
		Item item = itemStack.getItem();
		if (item instanceof MutandisBrew) {
			return ItemCategory.THROWABLE;
		}
		return DynamicCrosshairApi.super.getItemCategory(itemStack);
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof DragonbloodStaffItem) {
			if (context.player.isSneaking()) {
				return Crosshair.USABLE;
			}
		}
		if (item instanceof MutandisBrew) {
			return Crosshair.THROWABLE;
		}
		if (item instanceof MutandisItem) {
			if (context.isWithBlock() && context.getBlockState().isIn(BWPTags.MUTANDIS)) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof GobletBlock
				|| block instanceof LeechChestBlock
				|| block instanceof MimicChestBlock
				|| block instanceof StandingCandelabraBlock
				;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack itemStack = context.getItemStack();

		if (blockState.getBlock() instanceof GobletBlock && context.getBlockEntity() instanceof GobletBlockEntity gobletBlockEntity) {
			if (context.player.isSneaking() && itemStack.isEmpty()) {
				return Crosshair.INTERACTABLE;
			} else if (itemStack.isIn(BWPTags.GOBLET_LIQUIDS)) {
				if (gobletBlockEntity.getStack(0).isEmpty()) {
					return Crosshair.USABLE;
				}
			} else if (itemStack.getItem() == Items.GLASS_BOTTLE && !gobletBlockEntity.getStack(0).isEmpty()) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof LeechChestBlock) {
			if (context.player.isSneaking() && itemStack.isOf(BWObjects.TAGLOCK)) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof MimicChestBlock mimic) {
			if (!context.player.isSneaking() || !itemStack.isOf(BWObjects.TAGLOCK)) {
				return Crosshair.INTERACTABLE;
			}

			if (mimic.getLeechedPlayer() != null) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof StandingCandelabraBlock) {
			if (!blockState.get(Properties.WATERLOGGED) && blockState.get(StandingCandelabraBlock.HALF) == DoubleBlockHalf.UPPER) {
				if (itemStack.getItem() instanceof FlintAndSteelItem && !blockState.get(Properties.LIT)) {
					return Crosshair.USABLE;
				}

				if (itemStack.isEmpty() && blockState.get(Properties.LIT)) {
					return Crosshair.INTERACTABLE;
				}
			}
		}

		if (block instanceof UnicornPuddleBlock) {
			if (itemStack.getItem() instanceof GlassBottleItem || itemStack.getItem() instanceof GobletBlockItem) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
