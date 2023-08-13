package mod.crend.dynamiccrosshair.compat.galosphere;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.orcinus.galosphere.Galosphere;
import net.orcinus.galosphere.api.GoldenBreath;
import net.orcinus.galosphere.blocks.CombustionTableBlock;
import net.orcinus.galosphere.blocks.WarpedAnchorBlock;
import net.orcinus.galosphere.entities.Sparkle;
import net.orcinus.galosphere.entities.Spectre;
import net.orcinus.galosphere.init.GBlocks;
import net.orcinus.galosphere.init.GItemTags;
import net.orcinus.galosphere.init.GItems;
import net.orcinus.galosphere.items.*;

public class ApiImplGalosphere implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return Galosphere.MODID;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof SilverBombItem;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof GlowFlareItem
				|| item instanceof LichenCordycepsItem
				|| item instanceof GoldenLichenCordycepsItem
				|| item instanceof SpectreBottleItem
				;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof LichenCordycepsItem || item instanceof GoldenLichenCordycepsItem) {
			if (item instanceof GoldenLichenCordycepsItem && context.player.getAir() < ((GoldenBreath) context.player).getMaxGoldenAirSupply()) {
				return Crosshair.USABLE;
			}
			if (context.player.getAir() < context.player.getMaxAir()) {
				return Crosshair.USABLE;
			}
			if (itemStack.getItem().getFoodComponent() != null) {
				if (context.player.canConsume(itemStack.getItem().getFoodComponent().isAlwaysEdible())) {
					return Crosshair.USABLE;
				}
			}
		}

		if (context.isWithBlock()) {
			if (item instanceof GlowFlareItem || item instanceof SpectreBottleItem) {
				return Crosshair.USABLE;
			}
		}
		return null;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof CombustionTableBlock;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof WarpedAnchorBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack itemStack = context.getItemStack();

		if (block instanceof WarpedAnchorBlock) {
			if (itemStack.isOf(GBlocks.ALLURITE_BLOCK.asItem()) && blockState.get(WarpedAnchorBlock.WARPED_CHARGE) < 4) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}

	@Override
	public boolean isInteractableEntity(Entity entity) {
		return entity instanceof Sparkle || entity instanceof Spectre;
	}

	@Override
	public Crosshair computeFromEntity(CrosshairContext context) {
		Entity entity = context.getEntity();
		ItemStack itemStack = context.getItemStack();

		if (entity instanceof Sparkle sparkle) {
			if (sparkle.getVariant() != Sparkle.CrystalType.NONE && itemStack.getItem() instanceof PickaxeItem && !sparkle.isBaby()) {
				return Crosshair.USABLE;
			} else if (sparkle.getVariant() == Sparkle.CrystalType.NONE && itemStack.isIn(GItemTags.SPARKLE_TEMPT_ITEMS)) {
				return Crosshair.USABLE;
			}
		}

		if (entity instanceof Spectre spectre) {
			if (spectre.canBeManipulated() && (itemStack.isOf(GItems.SPECTRE_BOUND_SPYGLASS) || itemStack.isOf(Items.SPYGLASS))) {
				return Crosshair.USABLE;
			} else if (itemStack.isOf(Items.GLASS_BOTTLE)) {
				return Crosshair.USABLE;
			} else if (itemStack.isOf(GItems.ALLURITE_SHARD) && spectre.getManipulatorUUID() == null && !spectre.canBeManipulated()) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
