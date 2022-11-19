package mod.crend.dynamiccrosshair.compat.appliedenergistics;

import appeng.api.features.P2PTunnelAttunement;
import appeng.api.implementations.items.IMemoryCard;
import appeng.api.parts.*;
import appeng.api.upgrades.IUpgradeInventory;
import appeng.api.upgrades.IUpgradeableObject;
import appeng.block.AEBaseEntityBlock;
import appeng.block.crafting.AbstractCraftingUnitBlock;
import appeng.block.crafting.CraftingBlockItem;
import appeng.block.crafting.MolecularAssemblerBlock;
import appeng.block.crafting.PatternProviderBlock;
import appeng.block.misc.*;
import appeng.block.networking.CableBusBlock;
import appeng.block.networking.ControllerBlock;
import appeng.block.networking.WirelessBlock;
import appeng.block.qnb.QuantumLinkChamberBlock;
import appeng.block.spatial.SpatialAnchorBlock;
import appeng.block.spatial.SpatialIOPortBlock;
import appeng.block.storage.*;
import appeng.blockentity.crafting.CraftingBlockEntity;
import appeng.blockentity.networking.CableBusBlockEntity;
import appeng.blockentity.storage.SkyStoneTankBlockEntity;
import appeng.core.AEConfig;
import appeng.facade.FacadePart;
import appeng.items.materials.UpgradeCardItem;
import appeng.items.parts.FacadeItem;
import appeng.items.parts.PartItem;
import appeng.items.storage.BasicStorageCell;
import appeng.items.tools.powered.*;
import appeng.items.tools.quartz.QuartzCuttingKnifeItem;
import appeng.parts.PartPlacement;
import appeng.parts.automation.EnergyLevelEmitterPart;
import appeng.parts.automation.FormationPlanePart;
import appeng.parts.automation.IOBusPart;
import appeng.parts.automation.StorageLevelEmitterPart;
import appeng.parts.crafting.PatternProviderPart;
import appeng.parts.misc.InterfacePart;
import appeng.parts.p2p.P2PTunnelPart;
import appeng.parts.reporting.*;
import appeng.parts.storagebus.StorageBusPart;
import appeng.util.InteractionUtil;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.HitResult;

public class AE2Handler {

	public static boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return (item instanceof PortableCellItem
				|| item instanceof QuartzCuttingKnifeItem
		);
	}

	public static boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof CraftingBlockItem
				|| item instanceof UpgradeCardItem
				|| item instanceof FacadeItem
				|| item instanceof PartItem<?>
				|| item instanceof BasicStorageCell
				|| item instanceof ColorApplicatorItem
				|| item instanceof EntropyManipulatorItem
				|| item instanceof MatterCannonItem
				|| item instanceof WirelessTerminalItem;
	}

	public static Crosshair checkUsableItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof UpgradeCardItem) {
			if (context.player.isSneaking()) {
				IUpgradeInventory upgrades = null;
				BlockEntity be = context.getBlockEntity();
				if (be instanceof IPartHost partHost) {
					SelectedPart sp = partHost.selectPartWorld(context.hitResult.getPos());
					if (sp.part instanceof IUpgradeableObject) {
						upgrades = ((IUpgradeableObject)sp.part).getUpgrades();
					}
				} else if (be instanceof IUpgradeableObject) {
					upgrades = ((IUpgradeableObject)be).getUpgrades();
				}
				if (upgrades != null && upgrades.size() > 0) {
					return Crosshair.USABLE;
				}
			}
		}
		if (item instanceof CraftingBlockItem) {
			if (AEConfig.instance().isDisassemblyCraftingEnabled() && context.player.isSneaking()) {
				return Crosshair.USABLE;
			}
		}
		if (item instanceof FacadeItem facadeItem && context.isWithBlock()) {
			FacadePart facade = facadeItem.createPartFromItemStack(itemStack, context.getBlockHitSide());
			IPartHost host = PartHelper.getPartHost(context.world, context.getBlockPos());
			if (facade != null && host != null && FacadeItem.canPlaceFacade(host, facade)) {
				return Crosshair.HOLDING_BLOCK;
			}
		}
		if (item instanceof PartItem<?> && context.isWithBlock()) {
			if (PartPlacement.canPlacePartOnBlock(context.player, context.world, itemStack, context.getBlockPos(), context.getBlockHitSide())) {
				return Crosshair.HOLDING_BLOCK;
			}
		}
		if (item instanceof BasicStorageCell) {
			if (context.player.isSneaking()) {
				return Crosshair.USABLE;
			}
		}
		if (item instanceof ColorApplicatorItem applicator && applicator.getAECurrentPower(itemStack) > 100.0) {
			if (context.isWithBlock()) {
				return Crosshair.USABLE;
			}
			if (context.isWithEntity()) {
				Entity entity = context.getEntity();
				if (entity instanceof SheepEntity sheep && !sheep.isSheared()) {
					return Crosshair.USABLE;
				}
			}
		}
		if (item instanceof EntropyManipulatorItem manipulator && manipulator.getAECurrentPower(itemStack) > 1600.0) {
			if (context.isWithBlock() || context.raycastWithFluid().getType() == HitResult.Type.BLOCK) {
				return Crosshair.USABLE;
			}
		}
		if (item instanceof MatterCannonItem matterCannon && matterCannon.getAECurrentPower(itemStack) > 1600.0) {
			return Crosshair.THROWABLE;
		}
		if (item instanceof WirelessTerminalItem wirelessTerminal && wirelessTerminal.hasPower(context.player, 0.5, itemStack)) {
			return Crosshair.USABLE;
		}

		return null;
	}

	public static boolean isAlwaysInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return (   block instanceof CrankBlock
				|| block instanceof ControllerBlock
				|| block instanceof SkyChestBlock
		);
	}

	public static boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();

		return block instanceof AbstractCraftingUnitBlock<?>
			|| block instanceof MolecularAssemblerBlock
			|| block instanceof ChestBlock
			|| block instanceof DriveBlock
			|| block instanceof IOPortBlock
			|| block instanceof PatternProviderBlock
			|| block instanceof CellWorkbenchBlock
			|| block instanceof ChargerBlock
			|| block instanceof CondenserBlock
			|| block instanceof InscriberBlock
			|| block instanceof InterfaceBlock
			|| block instanceof SecurityStationBlock
			|| block instanceof VibrationChamberBlock
			|| block instanceof WirelessBlock
			|| block instanceof QuantumLinkChamberBlock
			|| block instanceof SpatialAnchorBlock
			|| block instanceof SpatialIOPortBlock
			|| block instanceof CrankBlock
			|| block instanceof TinyTNTBlock
			|| block instanceof SkyStoneTankBlock
			|| block instanceof ControllerBlock
			|| block instanceof CableBusBlock;
	}

	public static Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();

		if (block instanceof AEBaseEntityBlock<?>) {
			if (context.getItem() instanceof IMemoryCard && !(block instanceof CableBusBlock)) {
				return Crosshair.USABLE;
			}
		}
		if (block instanceof AbstractCraftingUnitBlock<?> && context.getBlockEntity() instanceof CraftingBlockEntity tg) {
			if (!context.player.isSneaking() && tg.isFormed() && tg.isActive()) {
				return Crosshair.INTERACTABLE;
			}
		}
		if (block instanceof MolecularAssemblerBlock
				|| block instanceof ChestBlock
				|| block instanceof DriveBlock
				|| block instanceof IOPortBlock
				|| block instanceof PatternProviderBlock
				|| block instanceof CellWorkbenchBlock
				|| block instanceof ChargerBlock
				|| block instanceof CondenserBlock
				|| block instanceof InscriberBlock
				|| block instanceof InterfaceBlock
				|| block instanceof SecurityStationBlock
				|| block instanceof VibrationChamberBlock
				|| block instanceof WirelessBlock
				|| block instanceof QuantumLinkChamberBlock
				|| block instanceof SpatialAnchorBlock
				|| block instanceof SpatialIOPortBlock
		) {
			if (!context.player.isSneaking()) {
				return Crosshair.INTERACTABLE;
			}
		}
		if (block instanceof TinyTNTBlock && context.getItemStack().isOf(Items.FLINT_AND_STEEL)) {
			return Crosshair.USABLE;
		}
		if (block instanceof SkyStoneTankBlock && context.getBlockEntity() instanceof SkyStoneTankBlockEntity blockEntity) {
			if (context.canInteractWithFluidStorage(blockEntity.getStorage())) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof CableBusBlock && context.isMainHand()) {
			if (context.getBlockEntity() instanceof CableBusBlockEntity busBlockEntity) {
				IPart part = busBlockEntity.selectPartWorld(context.hitResult.getPos()).part;

				if (part instanceof EnergyLevelEmitterPart
						|| part instanceof FormationPlanePart
						|| part instanceof IOBusPart
						|| part instanceof StorageLevelEmitterPart
						|| part instanceof PatternProviderPart
						|| part instanceof InterfacePart
						|| part instanceof StorageBusPart
				) {
					return Crosshair.INTERACTABLE;
				}

				if (part instanceof P2PTunnelPart<?> p2pTunnelPart) {
					ItemStack itemStack = context.getItemStack();
					if (itemStack.getItem() instanceof IMemoryCard) {
						return Crosshair.USABLE;
					}

					ItemStack newType = P2PTunnelAttunement.getTunnelPartByTriggerItem(itemStack);
					if (!newType.isEmpty()
							&& newType.getItem() != p2pTunnelPart.getPartItem()
							&& newType.getItem() instanceof IPartItem) {
						return Crosshair.USABLE;
					}
				}

				if (part instanceof AbstractMonitorPart monitor) {
					if (monitor.getMainNode().isActive()) {
						return Crosshair.INTERACTABLE;
					}
				}

				if (part instanceof AbstractReportingPart) {
					if (InteractionUtil.canWrenchRotate(context.getItemStack())) {
						return Crosshair.USABLE;
					}
				}

				if (part instanceof ConversionMonitorPart monitorPart) {
					if (monitorPart.getMainNode().isActive()) {
						return Crosshair.INTERACTABLE;
					}
				} else if (part instanceof AbstractTerminalPart) {
					return Crosshair.INTERACTABLE;
				}

				if (part instanceof PatternAccessTerminalPart) {
					return Crosshair.INTERACTABLE;
				}

			}
		}

		return null;
	}
}
