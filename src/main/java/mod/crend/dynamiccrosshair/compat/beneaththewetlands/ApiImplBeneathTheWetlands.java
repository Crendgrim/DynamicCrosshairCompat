package mod.crend.dynamiccrosshair.compat.beneaththewetlands;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.vedycrew.wetlands.WetlandsMod;
import net.vedycrew.wetlands.block.GearBlock;
import net.vedycrew.wetlands.block.WetlandsBlocks;
import net.vedycrew.wetlands.entity.custom.SwampGolemEntity;
import net.vedycrew.wetlands.entity.custom.WispEntity;
import net.vedycrew.wetlands.item.WetlandsItems;

public class ApiImplBeneathTheWetlands implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return WetlandsMod.MOD_ID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		return itemStack.isOf(WetlandsItems.BOTTLED_WISP);
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		return itemStack.isOf(WetlandsItems.SULFUR_NUGGET);
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (itemStack.isOf(WetlandsItems.SULFUR_NUGGET) && context.isWithBlock()) {
			BlockPos blockPos = context.getBlockPos();
			BlockState blockState = context.getBlockState();
			if (!CampfireBlock.canBeLit(blockState) && !CandleBlock.canBeLit(blockState) && !CandleCakeBlock.canBeLit(blockState)) {
				BlockPos blockPos2 = blockPos.offset(context.getBlockHitSide());
				if (AbstractFireBlock.canPlaceAt(context.world, blockPos2, context.player.getHorizontalFacing())) {
					return Crosshair.USABLE;
				}
			} else {
				return Crosshair.USABLE;
			}
		}
		return null;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return blockState.getBlock() == WetlandsBlocks.GEAR_BLOCK;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack itemStack = context.getItemStack();

		if (block instanceof GearBlock) {
			if (block != WetlandsBlocks.GEAR_BLOCK_ACTIVE && itemStack.isOf(WetlandsItems.BOTTLED_WISP)) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}

	@Override
	public boolean isInteractableEntity(Entity entity) {
		return entity instanceof WispEntity;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();
		ItemStack itemStack = context.getItemStack();

		if (entity instanceof WispEntity && itemStack.isOf(Items.GLASS_BOTTLE)) {
			return Crosshair.USABLE;
		}

		if (entity instanceof SwampGolemEntity golem) {
			if (itemStack.isOf(Items.SHEARS) && !golem.getUneared()) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
