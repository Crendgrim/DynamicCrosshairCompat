package mod.crend.dynamiccrosshair.compat.create;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;
import com.simibubi.create.content.contraptions.actors.seat.SeatEntity;
import com.simibubi.create.content.contraptions.bearing.ClockworkBearingBlock;
import com.simibubi.create.content.contraptions.bearing.MechanicalBearingBlock;
import com.simibubi.create.content.contraptions.bearing.SailBlock;
import com.simibubi.create.content.contraptions.bearing.WindmillBearingBlock;
import com.simibubi.create.content.contraptions.chassis.AbstractChassisBlock;
import com.simibubi.create.content.contraptions.gantry.GantryCarriageBlock;
import com.simibubi.create.content.contraptions.mounted.CartAssembleRailType;
import com.simibubi.create.content.contraptions.mounted.CartAssemblerBlock;
import com.simibubi.create.content.contraptions.piston.MechanicalPistonBlock;
import com.simibubi.create.content.contraptions.piston.PistonExtensionPoleBlock;
import com.simibubi.create.content.contraptions.pulley.PulleyBlock;
import com.simibubi.create.content.decoration.MetalLadderBlock;
import com.simibubi.create.content.decoration.encasing.EncasedBlock;
import com.simibubi.create.content.decoration.encasing.EncasingRegistry;
import com.simibubi.create.content.decoration.girder.GirderBlock;
import com.simibubi.create.content.decoration.placard.PlacardBlock;
import com.simibubi.create.content.decoration.placard.PlacardBlockEntity;
import com.simibubi.create.content.decoration.steamWhistle.WhistleBlock;
import com.simibubi.create.content.decoration.steamWhistle.WhistleExtenderBlock;
import com.simibubi.create.content.equipment.armor.BacktankBlock;
import com.simibubi.create.content.equipment.toolbox.ToolboxBlock;
import com.simibubi.create.content.fluids.drain.ItemDrainBlock;
import com.simibubi.create.content.fluids.drain.ItemDrainBlockEntity;
import com.simibubi.create.content.fluids.pipes.AxisPipeBlock;
import com.simibubi.create.content.fluids.pipes.FluidPipeBlock;
import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.belt.BeltBlock;
import com.simibubi.create.content.kinetics.belt.BeltBlockEntity;
import com.simibubi.create.content.kinetics.belt.BeltHelper;
import com.simibubi.create.content.kinetics.belt.BeltPart;
import com.simibubi.create.content.kinetics.crafter.MechanicalCrafterBlock;
import com.simibubi.create.content.kinetics.crank.HandCrankBlock;
import com.simibubi.create.content.kinetics.deployer.DeployerBlock;
import com.simibubi.create.content.kinetics.gantry.GantryShaftBlock;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlock;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlock;
import com.simibubi.create.content.kinetics.simpleRelays.CogWheelBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.content.kinetics.speedController.SpeedControllerBlock;
import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlock;
import com.simibubi.create.content.kinetics.steamEngine.SteamEngineBlock;
import com.simibubi.create.content.kinetics.transmission.sequencer.SequencedGearshiftBlock;
import com.simibubi.create.content.logistics.chute.AbstractChuteBlock;
import com.simibubi.create.content.logistics.chute.ChuteBlockEntity;
import com.simibubi.create.content.logistics.depot.DepotBlock;
import com.simibubi.create.content.logistics.depot.EjectorBlock;
import com.simibubi.create.content.logistics.funnel.FunnelBlock;
import com.simibubi.create.content.logistics.tunnel.BrassTunnelBlock;
import com.simibubi.create.content.logistics.tunnel.BrassTunnelBlockEntity;
import com.simibubi.create.content.processing.basin.BasinBlock;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.content.processing.burner.LitBlazeBurnerBlock;
import com.simibubi.create.content.redstone.analogLever.AnalogLeverBlock;
import com.simibubi.create.content.redstone.diodes.BrassDiodeBlock;
import com.simibubi.create.content.redstone.diodes.ToggleLatchBlock;
import com.simibubi.create.content.redstone.displayLink.DisplayLinkBlock;
import com.simibubi.create.content.redstone.link.RedstoneLinkBlock;
import com.simibubi.create.content.redstone.link.controller.LecternControllerBlock;
import com.simibubi.create.content.redstone.link.controller.LecternControllerBlockEntity;
import com.simibubi.create.content.redstone.nixieTube.NixieTubeBlock;
import com.simibubi.create.content.redstone.nixieTube.NixieTubeBlockEntity;
import com.simibubi.create.content.redstone.thresholdSwitch.ThresholdSwitchBlock;
import com.simibubi.create.content.schematics.cannon.SchematicannonBlock;
import com.simibubi.create.content.schematics.table.SchematicTableBlock;
import com.simibubi.create.content.trains.display.FlapDisplayBlock;
import com.simibubi.create.content.trains.display.FlapDisplayBlockEntity;
import com.simibubi.create.content.trains.station.StationBlock;
import com.simibubi.create.content.trains.station.StationBlockEntity;
import com.simibubi.create.content.trains.track.TrackBlock;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import com.simibubi.create.foundation.placement.IPlacementHelper;
import com.simibubi.create.foundation.placement.PlacementHelpers;
import com.simibubi.create.foundation.utility.Iterate;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import io.github.fabricators_of_create.porting_lib.util.TagUtil;
import me.alphamode.forgetags.Tags;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.compat.mixin.create.*;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.fabric.api.CrosshairFluidContext;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

import java.util.List;
import java.util.Map;

public class CreateBlockHandler {

	public static boolean isAlwaysInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof HandCrankBlock
				|| block instanceof AnalogLeverBlock
				|| block instanceof SchematicannonBlock
				|| block instanceof SchematicTableBlock
				;
	}

	public static boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof SeatBlock
				|| block instanceof MechanicalCrafterBlock
				|| block instanceof DeployerBlock
				|| block instanceof MillstoneBlock
				|| block instanceof ClockworkBearingBlock
				|| block instanceof MechanicalBearingBlock
				|| block instanceof WindmillBearingBlock
				|| block instanceof GantryCarriageBlock
				|| block instanceof CartAssemblerBlock
				|| block instanceof MechanicalPistonBlock
				|| block instanceof PulleyBlock
				|| block instanceof ItemDrainBlock
				|| block instanceof FluidTankBlock
				|| block instanceof BlazeBurnerBlock || block instanceof LitBlazeBurnerBlock
				|| block instanceof BasinBlock
				|| block instanceof SequencedGearshiftBlock
				|| block instanceof BeltBlock
				|| block instanceof CogWheelBlock
				|| block instanceof ShaftBlock
				|| block instanceof BacktankBlock
				|| block instanceof PlacardBlock
				|| block instanceof ToolboxBlock
				|| block instanceof DepotBlock
				|| block instanceof BrassDiodeBlock
				|| block instanceof ToggleLatchBlock
				|| block instanceof DisplayLinkBlock
				|| block instanceof NixieTubeBlock
				|| block instanceof RedstoneLinkBlock
				|| block instanceof ThresholdSwitchBlock
				|| block instanceof LecternControllerBlock
				|| block instanceof FlapDisplayBlock
				|| block instanceof StationBlock
				;
	}

	private static boolean isEncasable(Block block, ItemStack heldItem) {
		List<Block> encasedVariants = EncasingRegistry.getVariants(block);
		for (Block variant : encasedVariants) {
			if (variant instanceof EncasedBlock encased && encased.getCasing().asItem() == heldItem.getItem()) {
				return true;
			}
		}
		return false;
	}

	public static Crosshair computeFromBlock(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		ItemStack heldItem = context.getItemStack();

		if (block instanceof SeatBlock seatBlock && !context.player.isSneaking()) {
			DyeColor color = TagUtil.getColorFromStack(heldItem);
			if (color != null && color != seatBlock.getColor()) {
				return Crosshair.USABLE;
			}
			List<SeatEntity> seats = context.world.getNonSpectatingEntities(SeatEntity.class, new Box(context.getBlockPos()));
			if (seats.isEmpty()) {
				return Crosshair.INTERACTABLE;
			} else {
				List<Entity> passengers = seats.get(0).getPassengerList();
				if (passengers.isEmpty() || passengers.get(0) instanceof PlayerEntity) {
					return Crosshair.INTERACTABLE;
				}
			}
		}

		if (block instanceof MechanicalCrafterBlock) {
			if (!AllBlocks.MECHANICAL_ARM.isIn(heldItem)) {
				if (context.getBlockHitSide() == blockState.get(HorizontalKineticBlock.HORIZONTAL_FACING)) {
					// TODO cannot access crafter.phase
					return Crosshair.INTERACTABLE;
				}
			}
		}

		if (block instanceof DeployerBlock) {
			if (!AllItems.WRENCH.isIn(heldItem) && context.getBlockHitSide() == blockState.get(DirectionalKineticBlock.FACING)) {
				return Crosshair.INTERACTABLE;
			}
		}

		if (block instanceof MillstoneBlock) {
			if (heldItem.isEmpty()) {
				return Crosshair.INTERACTABLE;
			}
		}

		// contraptions.components.steam
		if (block instanceof WhistleBlock || block instanceof WhistleExtenderBlock) {
			if (AllBlocks.STEAM_WHISTLE.isIn(heldItem)) {
				return Crosshair.USABLE;
			}
		}

		if (block instanceof PoweredShaftBlock) {
			if (!context.player.isSneaking()) {
				IPlacementHelper helper = PlacementHelpers.get(ShaftBlock.placementHelperId);
				if (helper.matchesItem(heldItem)) {
					return Crosshair.HOLDING_BLOCK;
				}
			}
		}

		if (block instanceof SteamEngineBlock) {
			IPlacementHelper helper = PlacementHelpers.get(SteamEngineBlockAccessor.getPlacementHelperId());
			if (helper.matchesItem(heldItem)) {
				return Crosshair.HOLDING_BLOCK;
			}
		}

		// contraptions.components.structureMovement.bearing
		if (block instanceof ClockworkBearingBlock
				|| block instanceof MechanicalBearingBlock
				|| block instanceof WindmillBearingBlock) {
			if (!context.player.isSneaking() && heldItem.isEmpty()) {
				return Crosshair.INTERACTABLE;
			}
		}

		if (block instanceof SailBlock sail) {
			IPlacementHelper placementHelper = PlacementHelpers.get(SailBlockAccessor.getPlacementHelperId());
			if (placementHelper.matchesItem(heldItem)) {
				return Crosshair.HOLDING_BLOCK;
			} else if (heldItem.getItem() instanceof ShearsItem) {
				return Crosshair.USABLE;
			} else if (!sail.isFrame()) {
				DyeColor color = TagUtil.getColorFromStack(heldItem);
				if (color != null) {
					return Crosshair.USABLE;
				}
			}
		}

		// contraptions.components.structureMovement.chassis
		if (block instanceof AbstractChassisBlock chassis) {
			boolean isSlimeBall = heldItem.isIn(Tags.Items.SLIMEBALLS) || AllItems.SUPER_GLUE.isIn(heldItem);
			BooleanProperty affectedSide = chassis.getGlueableSide(blockState, context.getBlockHitSide());
			if (affectedSide == null) {
				return null;
			} else if (isSlimeBall && blockState.get(affectedSide)) {
				for (Direction face : Iterate.directions) {
					BooleanProperty glueableSide = chassis.getGlueableSide(blockState, face);
					if (glueableSide != null
							&& !(Boolean) blockState.get(glueableSide)
							&& ((AbstractChassisBlockAccessor) chassis).invokeGlueAllowedOnSide(context.world, context.getBlockPos(), blockState, face)) {
						return Crosshair.USABLE;
					}
				}
			} else {
				if ((((heldItem.isEmpty() && context.player.isSneaking()) || isSlimeBall) && blockState.get(affectedSide) != isSlimeBall)
						&& ((AbstractChassisBlockAccessor) chassis).invokeGlueAllowedOnSide(context.world, context.getBlockPos(), blockState, context.getBlockHitSide())) {
					return Crosshair.USABLE;
				}
			}
		}

		// contraptions.components.structureMovement.gantry
		if (block instanceof GantryCarriageBlock) {
			if (!context.player.isSneaking() && heldItem.isEmpty()) {
				return Crosshair.INTERACTABLE;
			}
		}

		// contraptions.components.structureMovement.mounted
		if (block instanceof CartAssemblerBlock) {
			Item previousItem = blockState.get(CartAssemblerBlock.RAIL_TYPE).getItem();
			if (!heldItem.isOf(previousItem)) {
				CartAssembleRailType newType = null;
				for (CartAssembleRailType type : CartAssembleRailType.values()) {
					if (heldItem.isOf(type.getItem())) {
						newType = type;
					}
				}

				if (newType != null) {
					return Crosshair.USABLE;
				}
			}
		}

		// contraptions.components.structureMovement.piston
		if (block instanceof MechanicalPistonBlock) {
			if (!context.player.isSneaking()) {
				if (heldItem.isEmpty()) {
					return Crosshair.INTERACTABLE;
				}
				if (heldItem.isIn(Tags.Items.SLIMEBALLS)
						&& blockState.get(MechanicalPistonBlock.STATE) == MechanicalPistonBlock.PistonState.RETRACTED
						&& context.getBlockHitSide() == blockState.get(MechanicalPistonBlock.FACING)
						&& !((MechanicalPistonBlockAccessor) block).getIsSticky()
				) {
					return Crosshair.USABLE;
				}
			}
		}
		if (block instanceof PistonExtensionPoleBlock) {
			IPlacementHelper placementHelper = PlacementHelpers.get(PistonExtensionPoleBlockAccessor.getPlacementHelperId());
			if (placementHelper.matchesItem(heldItem) && !context.player.isSneaking()) {
				return Crosshair.HOLDING_BLOCK;
			}
		}

		// contraptions.components.structureMovement.pulley
		if (block instanceof PulleyBlock) {
			if (!context.player.isSneaking() && heldItem.isEmpty()) {
				return Crosshair.INTERACTABLE;
			}
		}

		// contraptions.fluids
		if (block instanceof ItemDrainBlock && context.getBlockEntity() instanceof ItemDrainBlockEntity te) {
			if (!(heldItem.getItem() instanceof BlockItem) || ContainerItemContext.withConstant(heldItem).find(FluidStorage.ITEM) != null) {
				if (CrosshairFluidContext.canInteractWithFluidStorage(context, te.getFluidStorage(context.getBlockHitSide()))) {
					return Crosshair.USABLE;
				}
			}
		}

		if (block instanceof AxisPipeBlock || block instanceof FluidPipeBlock) {
			if (AllBlocks.COPPER_CASING.isIn(heldItem)) {
				return Crosshair.HOLDING_BLOCK;
			}
		}

		if (block instanceof FluidTankBlock fluidTank && context.getBlockEntity() instanceof FluidTankBlockEntity te) {
			if (!heldItem.isEmpty() && (context.player.isCreative() || ((FluidTankBlockAccessor) fluidTank).getCreative())) {
				if (CrosshairFluidContext.canInteractWithFluidStorage(context, te.getFluidStorage(context.getBlockHitSide()))) {
					return Crosshair.USABLE;
				}
			}
		}

		// processing.burner
		if (block instanceof BlazeBurnerBlock && context.getBlockEntity() instanceof BlazeBurnerBlockEntity burner) {
			BlazeBurnerBlock.HeatLevel heat = blockState.get(BlazeBurnerBlock.HEAT_LEVEL);
			if (AllItems.GOGGLES.isIn(heldItem) && heat != BlazeBurnerBlock.HeatLevel.NONE) {
				if (!((BlazeBurnerBlockEntityAccessor) burner).getGoggles()) {
					return Crosshair.USABLE;
				}
			} else if (FilteringBehaviour.playerCanInteract(context.player)) {
				if (heldItem.isEmpty() && heat != BlazeBurnerBlock.HeatLevel.NONE) {
					if (((BlazeBurnerBlockEntityAccessor) burner).getGoggles()) {
						return Crosshair.USABLE;
					}
				} else if (heat == BlazeBurnerBlock.HeatLevel.NONE) {
					if (heldItem.getItem() instanceof FlintAndSteelItem) {
						return Crosshair.USABLE;
					}
				} else {
					if (burner.isCreativeFuel(heldItem)) {
						return Crosshair.USABLE;
					}
					BlazeBurnerBlockEntity.FuelType newFuel = BlazeBurnerBlockEntity.FuelType.NONE;
					if (AllTags.AllItemTags.BLAZE_BURNER_FUEL_SPECIAL.matches(heldItem)) {
						newFuel = BlazeBurnerBlockEntity.FuelType.SPECIAL;
					} else if (FuelRegistry.INSTANCE.get(heldItem.getItem()) != null
							|| AllTags.AllItemTags.BLAZE_BURNER_FUEL_REGULAR.matches(heldItem)
					) {
						newFuel = BlazeBurnerBlockEntity.FuelType.NORMAL;
					}
					if (newFuel != BlazeBurnerBlockEntity.FuelType.NONE && newFuel.ordinal() >= burner.getActiveFuel().ordinal()) {
						if (newFuel != burner.getActiveFuel()
								|| burner.getRemainingBurnTime() <= 500
								|| newFuel == BlazeBurnerBlockEntity.FuelType.NORMAL
						) {
							return Crosshair.USABLE;
						}
					}
				}
			}
		}
		if (block instanceof LitBlazeBurnerBlock) {
			if (heldItem.getItem() instanceof ShovelItem) {
				return Crosshair.USABLE;
			} else if (blockState.get(LitBlazeBurnerBlock.FLAME_TYPE) == LitBlazeBurnerBlock.FlameType.REGULAR && heldItem.isIn(ItemTags.SOUL_FIRE_BASE_BLOCKS)) {
				return Crosshair.USABLE;
			}
		}

		// processing
		if (block instanceof BasinBlock && context.getBlockEntity() instanceof BasinBlockEntity te) {
			Direction direction = context.getBlockHitSide();
			if (heldItem.isEmpty()) {
				Storage<ItemVariant> inv = te.getItemStorage(direction);
				if (inv != null) {
					return Crosshair.INTERACTABLE;
				}
			} else {
				Storage<FluidVariant> storage = te.getFluidStorage(direction);
				if (CrosshairFluidContext.canInteractWithFluidStorage(context, storage)) {
					return Crosshair.USABLE;
				}

				if (heldItem.getItem().equals(Items.SPONGE)) {
					if (storage != null) {
						Transaction tx = TransferUtil.getTransaction();
						if (!TransferUtil.extractAnyFluid(storage, Long.MAX_VALUE, tx).isEmpty()) {
							tx.abort();
							return Crosshair.USABLE;
						}
						tx.abort();
					}
				}
			}
		}

		// relays.advanced
		if (block instanceof SequencedGearshiftBlock gearshift) {
			if (FilteringBehaviour.playerCanInteract(context.player)) {
				if (!AllItems.WRENCH.isIn(heldItem)) {
					if (!(heldItem.getItem() instanceof BlockItem blockItem
							&& blockItem.getBlock() instanceof KineticBlock
							&& gearshift.hasShaftTowards(context.world, context.getBlockPos(), blockState, context.getBlockHitSide())
					)) {
						return Crosshair.INTERACTABLE;
					}
				}
			}
		}
		if (block instanceof GantryShaftBlock) {
			IPlacementHelper placementHelper = PlacementHelpers.get(GantryShaftBlockAccessor.getPlacementHelperId());
			if (placementHelper.matchesItem(heldItem)) {
				return Crosshair.HOLDING_BLOCK;
			}
		}
		if (block instanceof SpeedControllerBlock) {
			IPlacementHelper placementHelper = PlacementHelpers.get(SpeedControllerBlockAccessor.getPlacementHelperId());
			if (placementHelper.matchesItem(heldItem)) {
				return Crosshair.HOLDING_BLOCK;
			}
		}
		if (block instanceof BeltBlock) {
			if (!context.player.isSneaking() && context.player.canModifyBlocks()) {
				boolean isWrench = AllItems.WRENCH.isIn(heldItem);
				boolean isConnector = AllItems.BELT_CONNECTOR.isIn(heldItem);
				boolean isShaft = AllBlocks.SHAFT.isIn(heldItem);
				boolean isDye = heldItem.isIn(Tags.Items.DYES);
				boolean hasWater = (GenericItemEmptying.emptyItem(context.world, heldItem, true).getFirst()).getFluid().matchesType(Fluids.WATER);
				boolean isHand = heldItem.isEmpty() && context.isMainHand();
				if (!isDye && !hasWater) {
					if (isConnector) {
						return Crosshair.USABLE;
					} else if (isWrench) {
						// TODO wrench
						return Crosshair.USABLE;
					} else {
						BeltBlockEntity belt = BeltHelper.getSegmentBE(context.world, context.getBlockPos());
						if (belt != null) {
							if (isHand) {
								BeltBlockEntity controllerBelt = belt.getControllerBE();
								if (controllerBelt != null) {
									return Crosshair.INTERACTABLE;
								}
								return null;
							} else if (isShaft) {
								if (blockState.get(BeltBlock.PART) != BeltPart.MIDDLE) {
									return Crosshair.USABLE;
								}
							} else if (AllBlocks.BRASS_CASING.isIn(heldItem) || AllBlocks.ANDESITE_CASING.isIn(heldItem)) {
								return Crosshair.HOLDING_BLOCK;
							}
						}
					}
				} else {
					return Crosshair.USABLE;
				}
			}
		}
		if (block instanceof CogWheelBlock) {
			if (!context.player.isSneaking() && context.player.canModifyBlocks()) {
				if (isEncasable(block, heldItem)) {
					return Crosshair.USABLE;
				}
			}
		}
		if (block instanceof ShaftBlock) {
			if (!context.player.isSneaking() && context.player.canModifyBlocks()) {
				if (isEncasable(block, heldItem)) {
					return Crosshair.USABLE;
				}

				if (AllBlocks.METAL_GIRDER.isIn(heldItem) && blockState.get(ShaftBlock.AXIS) != Direction.Axis.Y) {
					return Crosshair.HOLDING_BLOCK;
				} else {
					IPlacementHelper helper = PlacementHelpers.get(ShaftBlock.placementHelperId);
					if (helper.matchesItem(heldItem)) {
						return Crosshair.HOLDING_BLOCK;
					}
				}
			}
		}
		if (block instanceof BacktankBlock) {
			if (!context.player.isSneaking()
					&& !(heldItem.getItem() instanceof BlockItem)
					&& context.player.getEquippedStack(EquipmentSlot.CHEST).isEmpty()
			) {
				return Crosshair.INTERACTABLE;
			}
		}

		// curiosities.deco
		if (block instanceof MetalLadderBlock) {
			IPlacementHelper helper = PlacementHelpers.get(MetalLadderBlockAccessor.getPlacementHelperId());
			if (helper.matchesItem(heldItem)) {
				return Crosshair.HOLDING_BLOCK;
			}
		}
		if (block instanceof PlacardBlock && context.getBlockEntity() instanceof PlacardBlockEntity pte) {
			if (!context.player.isSneaking()) {
				ItemStack inBlock = pte.getHeldItem();
				if (context.player.canModifyBlocks() && !heldItem.isEmpty() && inBlock.isEmpty()) {
					return Crosshair.USABLE;
				}
				if (!inBlock.isEmpty() && !heldItem.isEmpty() && !blockState.get(PlacardBlock.POWERED)) {
					return Crosshair.INTERACTABLE;
				}
			}
		}

		// curiosities.girder
		if (block instanceof GirderBlock) {
			if (AllBlocks.SHAFT.isIn(heldItem)) {
				return Crosshair.USABLE;
			} else {
				if (AllItems.WRENCH.isIn(heldItem) && !context.player.bypassesSteppingEffects()) {
					if (blockState.get(GirderBlock.X) || blockState.get(GirderBlock.Z)) {
						return Crosshair.USABLE;
					}
				} else {
					IPlacementHelper helper = PlacementHelpers.get(GirderBlockAccessor.getPlacementHelperId());
					if (helper.matchesItem(heldItem)) {
						return Crosshair.HOLDING_BLOCK;
					}
				}
			}
		}

		// curiosities.toolbox
		if (block instanceof ToolboxBlock toolbox) {
			if (!context.player.isInSneakingPose()) {
				DyeColor color = TagUtil.getColorFromStack(heldItem);
				if (color != null && color != toolbox.getColor()) {
					return Crosshair.USABLE;
				} else {
					return Crosshair.INTERACTABLE;
				}
			}
		}

		// logistics.block
		if (block instanceof BrassTunnelBlock && context.getBlockEntity() instanceof BrassTunnelBlockEntity bte) {
			List<ItemStack> stacksOfGroup = bte.grabAllStacksOfGroup(true);
			if (!stacksOfGroup.isEmpty()) {
				return Crosshair.INTERACTABLE;
			}
		}

		if (block instanceof AbstractChuteBlock && context.getBlockEntity() instanceof ChuteBlockEntity chute) {
			if (heldItem.isEmpty() && !chute.getItem().isEmpty()) {
				return Crosshair.INTERACTABLE;
			}
		}

		if (block instanceof DepotBlock) {
			if (context.getBlockHitSide() == Direction.UP) {
				return Crosshair.INTERACTABLE;
			}
		}
		if (block instanceof EjectorBlock) {
			if (AllItems.WRENCH.isIn(heldItem) && context.getBlockHitSide() == Direction.UP) {
				return Crosshair.USABLE;
			}
		}

		// logistics.diodes
		if (block instanceof BrassDiodeBlock || block instanceof ToggleLatchBlock) {
			if (context.player.canModifyBlocks() && !context.player.isSneaking() && !AllItems.WRENCH.isIn(heldItem)) {
				return Crosshair.INTERACTABLE;
			}
		}

		// logistics.display
		if (block instanceof DisplayLinkBlock) {
			if (!context.player.isSneaking()) {
				return Crosshair.INTERACTABLE;
			}
		}

		// logistics.funnel
		if (block instanceof FunnelBlock) {
			if (!AllItems.WRENCH.isIn(heldItem) && context.getBlockHitSide() == FunnelBlock.getFunnelFacing(blockState)) {
				if (!heldItem.isEmpty()
						&& !AllBlocks.MECHANICAL_ARM.isIn(heldItem)
						&& !blockState.get(FunnelBlock.POWERED)
						&& !blockState.get(FunnelBlock.EXTRACTING)
				) {
					return Crosshair.USABLE;
				}
			}
		}

		// logistics.mechanicalArm
		if (block instanceof ArmBlock && context.getBlockEntity() instanceof ArmBlockEntity te) {
			if (!((ArmBlockEntityAccessor) te).getHeldItem().isEmpty()) {
				return Crosshair.INTERACTABLE;
			}
		}

		// logistics.redstone
		if (block instanceof NixieTubeBlock && context.getBlockEntity() instanceof NixieTubeBlockEntity nixie) {
			if (!context.player.isSneaking()) {
				if (heldItem.isEmpty()) {
					if (!nixie.reactsToRedstone()) {
						return Crosshair.INTERACTABLE;
					}
				} else {
					boolean display = heldItem.getItem() == Items.NAME_TAG && heldItem.hasCustomName();
					DyeColor dye = TagUtil.getColorFromStack(heldItem);
					if (display || dye != null) {
						return Crosshair.USABLE;
					}
				}
			}
		}
		if (block instanceof RedstoneLinkBlock link) {
			if (context.player.isSneaking() && link.playerCanToggle(context.player, context.world, context.getBlockPos())) {
				return Crosshair.INTERACTABLE;
			}
		}
		if (block instanceof ThresholdSwitchBlock) {
			if (!AllItems.WRENCH.isIn(heldItem)) {
				return Crosshair.INTERACTABLE;
			}
		}

		// logistics.item
		if (block instanceof LecternControllerBlock) {
			if (LecternControllerBlockEntity.playerInRange(context.player, context.world, context.getBlockPos())) {
				return Crosshair.INTERACTABLE;
			}
		}

		// logistics.trains
		if (block instanceof FlapDisplayBlock && context.getBlockEntity() instanceof FlapDisplayBlockEntity flapTe) {
			if (!context.player.isSneaking()) {
				IPlacementHelper placementHelper = PlacementHelpers.get(FlapDisplayBlockAcessor.getPlacementHelperId());
				if (placementHelper.matchesItem(heldItem)) {
					return Crosshair.HOLDING_BLOCK;
				} else {
					flapTe = flapTe.getController();
					if (flapTe != null) {
						if (heldItem.isEmpty()) {
							if (flapTe.isSpeedRequirementFulfilled()) {
								return Crosshair.INTERACTABLE;
							}
						} else if (heldItem.getItem() == Items.GLOW_INK_SAC) {
							return Crosshair.USABLE;
						} else {
							boolean display = heldItem.getItem() == Items.NAME_TAG && heldItem.hasCustomName();
							DyeColor dye = TagUtil.getColorFromStack(heldItem);
							if (dye != null || (display && flapTe.isSpeedRequirementFulfilled())) {
								return Crosshair.USABLE;
							}
						}
					}
				}
			}
		}

		if (block instanceof StationBlock && context.getBlockEntity() instanceof StationBlockEntity station) {
			if (!context.player.isSneaking() && !AllItems.WRENCH.isIn(heldItem)) {
				if (heldItem.getItem() == Items.FILLED_MAP) {
					if (station.getStation() != null && station.getStation().getId() != null) {
						return Crosshair.USABLE;
					}
				} else {
					return Crosshair.INTERACTABLE;
				}
			}
		}

		if (block instanceof TrackBlock) {
			for (Map.Entry<BlockPos, BlockBox> entry : StationBlockEntity.assemblyAreas.get(context.world).entrySet()) {
				if (entry.getValue().contains(context.getBlockPos())) {
					if (context.world.getBlockEntity(entry.getKey()) instanceof StationBlockEntity) {
						return Crosshair.INTERACTABLE;
					}
				}
			}

		}

		return null;
	}

}
