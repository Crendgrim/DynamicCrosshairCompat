package mod.crend.dynamiccrosshair.compat.dehydration;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.dehydration.access.ThirstManagerAccess;
import net.dehydration.block.AbstractCopperCauldronBlock;
import net.dehydration.block.CampfireCauldronBlock;
import net.dehydration.block.CopperLeveledCauldronBlock;
import net.dehydration.init.BlockInit;
import net.dehydration.init.ConfigInit;
import net.dehydration.item.LeatherFlask;
import net.dehydration.thirst.ThirstManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class ApiImplDehydration implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "dehydration";
	}

	@Override
	public boolean forceCheck() {
		return true;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		if (context.player.isSneaking() && context.isMainHand() && context.getItemStack().isEmpty()) {
			HitResult hitResult = context.player.raycast(1.5, 0.0F, true);
			BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
			if (context.world.getFluidState(blockPos).isIn(FluidTags.WATER) && (context.world.getFluidState(blockPos).isStill() || ConfigInit.CONFIG.allow_non_flowing_water_sip)) {
				ThirstManager thirstManager = ((ThirstManagerAccess) context.player).getThirstManager();
				if (thirstManager.isNotFull()) {
					return Crosshair.INTERACTABLE;
				}
			}
		}

		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();

		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (block instanceof CampfireCauldronBlock) {
			int level = blockState.get(CampfireCauldronBlock.LEVEL);
			if (level < 4) {
				if (item == Items.WATER_BUCKET) {
					return Crosshair.USABLE;
				}
			}
			if (level == 4) {
				if (item == Items.BUCKET) {
					return Crosshair.USABLE;
				}
			}
			if (level > 0) {
				if (item == Items.GLASS_BOTTLE || item instanceof LeatherFlask) {
					return Crosshair.USABLE;
				}
			}
		}

		if (block instanceof AbstractCopperCauldronBlock) {
			if (item == Items.WATER_BUCKET || item == Items.POWDER_SNOW_BUCKET) {
				return Crosshair.USABLE;
			}
			if (block instanceof CopperLeveledCauldronBlock) {
				if (block == BlockInit.COPPER_POWDERED_CAULDRON_BLOCK || block == BlockInit.COPPER_WATER_CAULDRON_BLOCK) {
					if (item == Items.BUCKET && blockState.get(CopperLeveledCauldronBlock.LEVEL) == 3) {
						return Crosshair.USABLE;
					}
				}
				if (block == BlockInit.COPPER_PURIFIED_WATER_CAULDRON_BLOCK || block == BlockInit.COPPER_WATER_CAULDRON_BLOCK) {
					if (item == Items.GLASS_BOTTLE) {
						return Crosshair.USABLE;
					}
				}
			}
		}

		return null;
	}
}