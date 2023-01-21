package mod.crend.dynamiccrosshair.compat.indrev;

import me.steven.indrev.IndustrialRevolution;
import me.steven.indrev.blockentities.MachineBlockEntity;
import me.steven.indrev.blockentities.cables.BasePipeBlockEntity;
import me.steven.indrev.blockentities.farms.BiomassComposterBlockEntity;
import me.steven.indrev.blockentities.laser.CapsuleBlockEntity;
import me.steven.indrev.blockentities.storage.ChargePadBlockEntity;
import me.steven.indrev.blocks.machine.CapsuleBlock;
import me.steven.indrev.blocks.machine.ChargePadBlock;
import me.steven.indrev.blocks.machine.DrillBlock;
import me.steven.indrev.blocks.machine.MachineBlock;
import me.steven.indrev.blocks.machine.pipes.BasePipeBlock;
import me.steven.indrev.blocks.machine.pipes.ItemPipeBlock;
import me.steven.indrev.blocks.machine.solarpowerplant.SolarPowerPlantTowerBlock;
import me.steven.indrev.blocks.misc.BiomassComposterBlock;
import me.steven.indrev.blocks.misc.CabinetBlock;
import me.steven.indrev.blocks.misc.TankBlock;
import me.steven.indrev.components.InventoryComponent;
import me.steven.indrev.items.energy.IRModularDrillItem;
import me.steven.indrev.items.misc.IREnergyReaderItem;
import me.steven.indrev.items.misc.IRGuideBookItem;
import me.steven.indrev.items.misc.IRMachineUpgradeItem;
import me.steven.indrev.items.misc.IRServoItem;
import me.steven.indrev.registry.IRBlockRegistry;
import me.steven.indrev.tools.modular.DrillModule;
import me.steven.indrev.utils.FluidutilsKt;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.fabric.api.CrosshairFluidContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;

public class ApiImplIndustrialRevolution implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return "indrev";
	}

	@Override
	public boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof IRGuideBookItem;
	}

	@Override
	public boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (item instanceof IRModularDrillItem
				|| item instanceof IREnergyReaderItem
				|| item instanceof IRMachineUpgradeItem
		);
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		if (!context.includeUsableItem()) return null;

		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();
		if (item instanceof IRModularDrillItem) {
			if ((!context.isWithBlock() && DrillModule.CONTROLLED_DESTRUCTION.getLevel(itemStack) > 0) || DrillModule.MATTER_PROJECTOR.getLevel(itemStack) > 0) {
				return Crosshair.USABLE;
			}
		}
		if (item instanceof IREnergyReaderItem) {
			if (context.isWithBlock()) {
				return Crosshair.USABLE;
			}
		}
		if (item instanceof IRMachineUpgradeItem upgradeItem && context.isWithBlock()) {
			BlockState blockState = context.getBlockState();
			if (blockState.getBlock() instanceof MachineBlock machineBlock && machineBlock.getTier() == upgradeItem.getFrom()) {
				return Crosshair.USABLE;
			}
		}
		if (item instanceof IRServoItem) {
			if (!context.isWithBlock()) {
				return Crosshair.USABLE;
			}
			if (context.isMainHand() && context.getBlockEntity() instanceof BasePipeBlockEntity pipeBlockEntity) {
				Direction dir = BasePipeBlock.Companion.getSideFromHit(context.hitResult.getPos(), context.getBlockPos());
				if (dir != null && pipeBlockEntity.getConnections().getOrDefault(dir, BasePipeBlock.ConnectionType.NONE).isConnected()) {
					return Crosshair.USABLE;
				}

			}
		}
		return null;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return (block instanceof SolarPowerPlantTowerBlock
				|| block instanceof CapsuleBlock
				|| block instanceof MachineBlock
				|| block instanceof BiomassComposterBlock
		);
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (block instanceof ItemPipeBlock && context.getBlockEntity() instanceof BasePipeBlockEntity pipeBlockEntity) {
			Direction dir = BasePipeBlock.Companion.getSideFromHit(context.hitResult.getPos(), context.getBlockPos());
			if (context.isEmptyHanded()
					&& dir != null
					&& pipeBlockEntity.getConnections().getOrDefault(dir, BasePipeBlock.ConnectionType.NONE).isConnected()
			) {
				return Crosshair.INTERACTABLE;
			}
		}
		if (block instanceof BasePipeBlock) {
			if (itemStack.isIn(IndustrialRevolution.INSTANCE.getWRENCH_TAG())) {
				return Crosshair.USABLE;
			}
			if (context.getBlockEntity() instanceof BasePipeBlockEntity pipeBlockEntity
					&& pipeBlockEntity.getCoverState() == null
					&& !context.player.isSneaking()
					&& item instanceof BlockItem blockItem
					&& !(item instanceof BlockEntityProvider)
					&& blockItem.getBlock().getDefaultState().isFullCube(context.world, context.getBlockPos())
			) {
				return Crosshair.HOLDING_BLOCK;
			}
		}
		if (block instanceof SolarPowerPlantTowerBlock) {
			if (itemStack.isIn(IndustrialRevolution.INSTANCE.getSCREWDRIVER_TAG())
					&& itemStack.hasNbt()
					&& itemStack.getNbt().contains("SelectedHeliostats")
			) {
				return Crosshair.USABLE;
			}
			return Crosshair.INTERACTABLE;
		}
		if (block instanceof CapsuleBlock && context.isMainHand() && context.getBlockEntity() instanceof CapsuleBlockEntity capsuleEntity) {
			if (capsuleEntity.getInventory().get(0).isEmpty()) {
				return Crosshair.USABLE;
			}
			return Crosshair.INTERACTABLE;
		}
		if (block instanceof ChargePadBlock && context.getBlockEntity() instanceof ChargePadBlockEntity chargePadEntity) {
			InventoryComponent ic = chargePadEntity.getInventoryComponent();
			if (ic == null) return null;
			if (!ic.getInventory().getStack(0).isEmpty()) {
				return Crosshair.INTERACTABLE;
			}
			return Crosshair.USABLE;
		}
		if (block instanceof DrillBlock
				|| block instanceof CabinetBlock
		) {
			return Crosshair.INTERACTABLE;
		}
		if (block instanceof TankBlock) {
			if (CrosshairFluidContext.canInteractWithFluidStorage(context, FluidutilsKt.fluidStorageOf(context.world, context.getBlockPos(), context.getBlockHitSide()))) {
				return Crosshair.USABLE;
			}
		}
		if (block instanceof MachineBlock && context.getBlockEntity() instanceof MachineBlockEntity<?> blockEntity) {
			if (blockEntity.getFluidComponent() != null) {
				if (CrosshairFluidContext.canInteractWithFluidStorage(context, blockEntity.getFluidComponent())) {
					return Crosshair.USABLE;
				}
			}
			if (!(item instanceof IRMachineUpgradeItem)) {
				if (itemStack.isIn(IndustrialRevolution.INSTANCE.getWRENCH_TAG())
						|| itemStack.isIn(IndustrialRevolution.INSTANCE.getSCREWDRIVER_TAG())) {
					return Crosshair.USABLE;
				}
				return Crosshair.INTERACTABLE;
			}
		}
		if (block instanceof BiomassComposterBlock && context.getBlockEntity() instanceof BiomassComposterBlockEntity blockEntity) {
			if (CrosshairFluidContext.canInteractWithFluidStorage(context, blockEntity.getFluidInv())) {
				return Crosshair.USABLE;
			}
			if (ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.containsKey(item) || itemStack.isEmpty()) {
				return Crosshair.USABLE;
			} else if (blockState.get(BiomassComposterBlock.Companion.getCLOSED())) {
				return Crosshair.INTERACTABLE;
			} else if (itemStack.isOf(IRBlockRegistry.INSTANCE.getPLANKS().asItem())) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
