package mod.crend.dynamiccrosshair.compat.additionaladditions;

import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.block.CopperPatinaBlock;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.ConfigValues;
import dqu.additionaladditions.item.*;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.compat.mixin.additionaladditions.ICopperPatinaBlockMixin;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;

import java.util.Optional;

public class ApiImplAdditionalAdditions implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return AdditionalAdditions.namespace;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();

		if (block instanceof CopperPatinaBlock) {
			if (ICopperPatinaBlockMixin.invokeIsFullyConnected(blockState) || ICopperPatinaBlockMixin.invokeIsNotConnected(blockState)) {
				return Crosshair.INTERACTABLE;
			}
		}

		return null;
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		if (item instanceof DepthMeterItem) {
			return !Config.getBool(ConfigValues.DEPTH_METER, "displayElevationAlways");
		}
		return item instanceof GlowStickItem
				|| item instanceof MysteriousBundleItem;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof CopperPatinaItem
				|| item instanceof WateringCanItem
				|| item instanceof WrenchItem
				|| item instanceof DepthMeterItem;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		ItemStack handItemStack = context.getItemStack();
		Item item = handItemStack.getItem();

		if (context.isWithBlock() && context.includeUsableItem()) {
			BlockState blockState = context.getBlockState();
			Block block = blockState.getBlock();

			if (item instanceof CopperPatinaItem) {
				Optional<Block> optional = Oxidizable.getIncreasedOxidationBlock(block);
				if (optional.isPresent() && !context.player.isSneaking()) {
					return Crosshair.USABLE;
				}
			}

			if (item instanceof WrenchItem) {
				if (!(block instanceof ChestBlock || block instanceof BedBlock)) {
					if (blockState.contains(Properties.FACING)
							|| blockState.contains(Properties.HOPPER_FACING)
							|| blockState.contains(Properties.HORIZONTAL_FACING)
							|| blockState.contains(Properties.AXIS)
							|| blockState.contains(Properties.HORIZONTAL_AXIS)
					) {
						return Crosshair.USABLE;
					}
					if (block instanceof SlabBlock) {
						if (!(blockState.get(Properties.SLAB_TYPE)).equals(SlabType.DOUBLE)) {
							return Crosshair.USABLE;
						}
					}
				}
			}
		}

		if (item instanceof WateringCanItem) {
			BlockHitResult hitResult = context.raycastWithFluid(RaycastContext.FluidHandling.SOURCE_ONLY);
			if (hitResult.getType() == HitResult.Type.BLOCK) {
				BlockPos wateredBlockPos = hitResult.getBlockPos();
				BlockState wateredBlockState = context.world.getBlockState(wateredBlockPos);
				Block wateredBlock = wateredBlockState.getBlock();
				if (handItemStack.getDamage() > 0 || context.player.isCreative()) {
					if (wateredBlock instanceof Fertilizable && !(wateredBlock instanceof GrassBlock)) {
						return Crosshair.USABLE;
					}
					if (wateredBlock instanceof FarmlandBlock) {
						return Crosshair.USABLE;
					}
				}

				if (handItemStack.getDamage() < 100) {
					if (wateredBlock == Blocks.WATER) {
						return Crosshair.USABLE;
					}
				}
			}
		}

		return null;
	}
}
