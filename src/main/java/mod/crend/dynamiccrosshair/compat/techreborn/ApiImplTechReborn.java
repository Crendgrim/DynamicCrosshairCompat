package mod.crend.dynamiccrosshair.compat.techreborn;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import reborncore.api.ToolManager;
import reborncore.common.fluid.container.ItemFluidInfo;
import techreborn.TechReborn;
import techreborn.blockentity.storage.fluid.TankUnitBaseBlockEntity;
import techreborn.blockentity.storage.item.StorageUnitBaseBlockEntity;
import techreborn.blocks.cable.CableBlock;
import techreborn.blocks.generator.BlockFusionControlComputer;
import techreborn.blocks.lighting.LampBlock;
import techreborn.blocks.machine.tier1.ResinBasinBlock;
import techreborn.blocks.misc.BlockAlarm;
import techreborn.blocks.misc.BlockRubberLog;
import techreborn.blocks.storage.energy.EnergyStorageBlock;
import techreborn.blocks.storage.energy.LSUStorageBlock;
import techreborn.blocks.storage.fluid.TankUnitBlock;
import techreborn.blocks.storage.item.StorageUnitBlock;
import techreborn.blocks.transformers.BlockTransformer;
import techreborn.init.TRContent;
import techreborn.items.*;
import techreborn.items.tool.DebugToolItem;
import techreborn.items.tool.PaintingToolItem;
import techreborn.items.tool.TreeTapItem;
import techreborn.items.tool.advanced.AdvancedJackhammerItem;
import techreborn.items.tool.basic.ElectricTreetapItem;
import techreborn.items.tool.industrial.IndustrialChainsawItem;
import techreborn.items.tool.industrial.IndustrialDrillItem;
import techreborn.items.tool.industrial.IndustrialJackhammerItem;
import techreborn.items.tool.industrial.NanosaberItem;

public class ApiImplTechReborn implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return TechReborn.MOD_ID;
	}

	long getStoredEnergy(ItemStack itemStack) {
		return (itemStack.hasNbt() ? itemStack.getNbt().getLong("energy") : 0);
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (item instanceof BatteryItem
				|| item instanceof FrequencyTransmitterItem
				|| item instanceof ManualItem
				|| item instanceof ScrapBoxItem
		);
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (RebornCoreHandler.isUsableItem(itemStack)
				|| item instanceof DynamicCellItem
				|| item instanceof UpgraderItem
				|| item instanceof DebugToolItem
				|| item instanceof PaintingToolItem
		);
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (!context.includeUsableItem()) return null;

		Crosshair crosshair = RebornCoreHandler.checkUsableItem(context);
		if (crosshair != null) return crosshair;

		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();
		Block block = context.getBlock();
		if (ToolManager.INSTANCE.canHandleTool(itemStack)) {
			if (block instanceof CableBlock
					|| block instanceof LampBlock
					|| block instanceof LSUStorageBlock
					|| block instanceof BlockTransformer
					|| block instanceof BlockAlarm
					|| block instanceof EnergyStorageBlock
			) {
				return Crosshair.USABLE;
			}
		}
		if (block instanceof BlockAlarm) {
			if (context.player.isSneaking()) {
				return Crosshair.INTERACTABLE;
			}
		}

		if (item instanceof DynamicCellItem cell && context.isWithBlock()) {
			Fluid containedFluid = cell.getFluid(itemStack);
			BlockHitResult fluidHitResult = context.raycastWithFluid(RaycastContext.FluidHandling.SOURCE_ONLY);
			BlockState fluidState = context.world.getBlockState(fluidHitResult.getBlockPos());
			if (containedFluid == Fluids.EMPTY) {
				if (fluidHitResult.getType() == HitResult.Type.BLOCK) {
					if (fluidState.getBlock() instanceof FluidDrainable) {
						return Crosshair.USABLE;
					}
				}

			} else if (containedFluid instanceof FlowableFluid) {
				if (fluidState.canBucketPlace(containedFluid)) {
					return Crosshair.HOLDING_BLOCK;
				}
			}
		}

		if (item instanceof UpgraderItem) {
			BlockEntity blockEntity = context.getBlockEntity();
			if (blockEntity instanceof StorageUnitBaseBlockEntity || blockEntity instanceof TankUnitBaseBlockEntity) {
				return Crosshair.USABLE;
			}
		}

		if (item instanceof AdvancedJackhammerItem
				|| item instanceof IndustrialChainsawItem
				|| item instanceof IndustrialDrillItem
				|| item instanceof IndustrialJackhammerItem
				|| item instanceof NanosaberItem
		) {
			if (context.player.isSneaking()) {
				return Crosshair.USABLE;
			}
		}
		if (item instanceof DebugToolItem) {
			if (context.isWithBlock()) {
				return Crosshair.USABLE;
			}
		}
		if (item instanceof PaintingToolItem && context.isWithBlock()) {
			BlockState blockState = context.getBlockState();
			if (context.player.isSneaking()) {
				if (blockState.isOpaqueFullCube(context.world, context.getBlockPos()) && blockState.getBlock().getDefaultState().isOpaqueFullCube(context.world, context.getBlockPos())) {
					return Crosshair.USABLE;
				}
			} else {
				BlockState cover = PaintingToolItem.getCover(itemStack);
				if (cover != null && blockState.getBlock() instanceof CableBlock && blockState.get(CableBlock.COVERED)) {
					return Crosshair.USABLE;
				}
			}
		}

		return null;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return (block instanceof StorageUnitBlock
				|| (block instanceof EnergyStorageBlock energyStorageBlock && energyStorageBlock.gui != null)
		);
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return (block instanceof BlockAlarm
				|| block instanceof ResinBasinBlock
				|| block instanceof EnergyStorageBlock
		);
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		Crosshair crosshair = RebornCoreHandler.computeFromBlock(context);
		if (crosshair != null) return crosshair;

		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack itemStack = context.getItemStack();
		if (block instanceof CableBlock cable && !itemStack.isEmpty()) {
			if (!blockState.get(CableBlock.COVERED) && !cable.type.canKill && itemStack.getItem() == TRContent.Plates.WOOD.asItem()) {
				return Crosshair.HOLDING_BLOCK;
			}
		}

		if (block instanceof BlockFusionControlComputer && !itemStack.isEmpty()) {
			if (itemStack.getItem() == TRContent.Machine.FUSION_COIL.asItem()) {
				return Crosshair.USABLE;
			}
		}
		if (block instanceof ResinBasinBlock) {
			if (blockState.get(ResinBasinBlock.FULL) && !blockState.get(ResinBasinBlock.POURING)) {
				return Crosshair.INTERACTABLE;
			}
		}

		if (block instanceof BlockRubberLog) {
			Item item = itemStack.getItem();
			if ((item instanceof ElectricTreetapItem && getStoredEnergy(itemStack) > 20L) || item instanceof TreeTapItem) {
				if (blockState.get(BlockRubberLog.HAS_SAP) && blockState.get(BlockRubberLog.SAP_SIDE) == context.getBlockHitSide()) {
					return Crosshair.USABLE;
				}
			}
		}
		if (block instanceof TankUnitBlock) {
			Item item = itemStack.getItem();
			if ((item instanceof DynamicCellItem || item instanceof BucketItem) && item instanceof ItemFluidInfo) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
