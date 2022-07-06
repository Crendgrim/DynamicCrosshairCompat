package mod.crend.dynamiccrosshair.compat.enderscape;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.bunten.enderscape.Enderscape;
import net.bunten.enderscape.blocks.CelestialMyceliumBlock;
import net.bunten.enderscape.blocks.FlangerBerryVine;
import net.bunten.enderscape.blocks.NebuliteCauldronBlock;
import net.bunten.enderscape.entity.drifter.DrifterEntity;
import net.bunten.enderscape.entity.driftlet.DriftletEntity;
import net.bunten.enderscape.items.FlangerBerryItem;
import net.bunten.enderscape.items.HealingItem;
import net.bunten.enderscape.items.MirrorItem;
import net.bunten.enderscape.items.NebuliteItem;
import net.bunten.enderscape.registry.EnderscapeBlocks;
import net.bunten.enderscape.registry.EnderscapeItems;
import net.bunten.enderscape.util.MirrorUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;

import static net.bunten.enderscape.blocks.FlangerBerryVine.AGE;
import static net.bunten.enderscape.blocks.FlangerBerryVine.ATTACHED;

public class ApiImplEnderscape implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Enderscape.MOD_ID;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof HealingItem
				|| item instanceof MirrorItem;
	}

	@Override
	public Crosshair checkUsableItem(CrosshairContext context) {
		ItemStack handItemStack = context.getItemStack();
		Item item = handItemStack.getItem();

		if (item instanceof HealingItem) {
			if (!context.player.getItemCooldownManager().isCoolingDown(item)) {
				return Crosshair.USE_ITEM;
			}
		}

		if (item instanceof MirrorItem) {
			if (MirrorUtil.isLinked(handItemStack) && MirrorUtil.isSameDimension(handItemStack, context.player) && MirrorUtil.hasEnoughEnergy(handItemStack, context.player)) {
				return Crosshair.USE_ITEM;
			}
		}

		if (context.isWithBlock()) {
			BlockState blockState = context.getBlockState();
			Block block = blockState.getBlock();

			if (block instanceof FlangerBerryVine) {
				if (item instanceof FlangerBerryItem) {
					return Crosshair.USE_ITEM;
				}

				if (item instanceof ShearsItem && blockState.get(AGE) < 15 && !blockState.get(ATTACHED)) {
					return Crosshair.USE_ITEM;
				}
			}

			if (item instanceof NebuliteItem) {
				if (blockState.isOf(Blocks.CAULDRON)) {
					return Crosshair.USE_ITEM;
				}
				if (blockState.isOf(EnderscapeBlocks.NEBULITE_CAULDRON) && NebuliteCauldronBlock.canLevel(context.world, context.getBlockPos(), blockState)) {
					return Crosshair.USE_ITEM;
				}
			}

			if (block instanceof CelestialMyceliumBlock && item instanceof ShovelItem && context.world.getBlockState(context.getBlockPos().up()).isAir()) {
				return Crosshair.USE_ITEM;
			}
		}

		return null;
	}

	@Override
	public Crosshair checkEntity(CrosshairContext context) {
		Entity entity = context.getEntity();

		if (entity instanceof DrifterEntity drifterEntity) {
			if (context.getItemStack().isOf(Items.GLASS_BOTTLE) && drifterEntity.isDrippingJelly()) {
				return Crosshair.USE_ITEM;
			}
		}

		if (entity instanceof DriftletEntity) {
			if (context.getItemStack().isIn(EnderscapeItems.DRIFTER_FOOD)) {
				return Crosshair.USE_ITEM;
			}
		}

		return null;
	}
}
