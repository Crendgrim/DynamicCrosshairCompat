package mod.crend.dynamiccrosshair.compat.create;

import com.google.common.collect.BiMap;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.base.CasingBlock;
import com.simibubi.create.content.contraptions.components.actors.AttachedActorBlock;
import com.simibubi.create.content.contraptions.components.crafter.MechanicalCrafterBlock;
import com.simibubi.create.content.contraptions.components.deployer.DeployerBlock;
import com.simibubi.create.content.contraptions.components.fan.NozzleBlock;
import com.simibubi.create.content.contraptions.components.steam.whistle.WhistleBlock;
import com.simibubi.create.content.contraptions.components.steam.whistle.WhistleExtenderBlock;
import com.simibubi.create.content.contraptions.components.structureMovement.mounted.CartAssembleRailType;
import com.simibubi.create.content.contraptions.components.structureMovement.mounted.CartAssemblerBlock;
import com.simibubi.create.content.contraptions.components.structureMovement.mounted.CartAssemblerBlockItem;
import com.simibubi.create.content.contraptions.components.structureMovement.mounted.MinecartContraptionItem;
import com.simibubi.create.content.contraptions.components.structureMovement.piston.MechanicalPistonBlock;
import com.simibubi.create.content.contraptions.components.tracks.ControllerRailBlock;
import com.simibubi.create.content.contraptions.fluids.FluidPropagator;
import com.simibubi.create.content.contraptions.fluids.pipes.*;
import com.simibubi.create.content.contraptions.fluids.tank.FluidTankBlock;
import com.simibubi.create.content.contraptions.goggles.GogglesItem;
import com.simibubi.create.content.contraptions.processing.BasinBlock;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlockItem;
import com.simibubi.create.content.contraptions.relays.advanced.sequencer.SequencedGearshiftBlock;
import com.simibubi.create.content.contraptions.relays.belt.BeltBlock;
import com.simibubi.create.content.contraptions.relays.belt.BeltPart;
import com.simibubi.create.content.contraptions.relays.belt.item.BeltConnectorItem;
import com.simibubi.create.content.contraptions.relays.elementary.BracketedTileEntityBehaviour;
import com.simibubi.create.content.contraptions.relays.encased.EncasedCogwheelBlock;
import com.simibubi.create.content.contraptions.wrench.IWrenchable;
import com.simibubi.create.content.contraptions.wrench.IWrenchableWithBracket;
import com.simibubi.create.content.contraptions.wrench.WrenchItem;
import com.simibubi.create.content.curiosities.ExperienceNuggetItem;
import com.simibubi.create.content.curiosities.TreeFertilizerItem;
import com.simibubi.create.content.curiosities.armor.CopperBacktankItem;
import com.simibubi.create.content.curiosities.girder.GirderBlock;
import com.simibubi.create.content.curiosities.symmetry.SymmetryWandItem;
import com.simibubi.create.content.curiosities.tools.BlueprintItem;
import com.simibubi.create.content.curiosities.tools.SandPaperItem;
import com.simibubi.create.content.curiosities.tools.SandPaperPolishingRecipe;
import com.simibubi.create.content.curiosities.weapons.PotatoCannonItem;
import com.simibubi.create.content.curiosities.zapper.ZapperItem;
import com.simibubi.create.content.logistics.block.belts.tunnel.BeltTunnelBlock;
import com.simibubi.create.content.logistics.block.chute.ChuteBlock;
import com.simibubi.create.content.logistics.block.depot.EjectorItem;
import com.simibubi.create.content.logistics.block.display.DisplayLinkBlockItem;
import com.simibubi.create.content.logistics.block.funnel.BeltFunnelBlock;
import com.simibubi.create.content.logistics.block.funnel.FunnelBlock;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmItem;
import com.simibubi.create.content.logistics.block.redstone.RedstoneLinkBlock;
import com.simibubi.create.content.logistics.block.vault.ItemVaultBlock;
import com.simibubi.create.content.logistics.item.LinkedControllerItem;
import com.simibubi.create.content.logistics.item.filter.FilterItem;
import com.simibubi.create.content.logistics.trains.ITrackBlock;
import com.simibubi.create.content.logistics.trains.management.edgePoint.TrackTargetingBlockItem;
import com.simibubi.create.content.logistics.trains.management.edgePoint.signal.SignalBlock;
import com.simibubi.create.content.logistics.trains.management.schedule.ScheduleItem;
import com.simibubi.create.content.logistics.trains.track.TrackBlock;
import com.simibubi.create.content.logistics.trains.track.TrackBlockItem;
import com.simibubi.create.content.schematics.item.SchematicItem;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.utility.Iterate;
import io.github.fabricators_of_create.porting_lib.mixin.common.accessor.AxeItemAccessor;
import io.github.fabricators_of_create.porting_lib.util.MinecartAndRailUtil;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.ItemCategory;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.mixin.IItemMixin;
import net.minecraft.block.*;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import java.util.Optional;

public class CreateItemHandler {

	public static ItemCategory getItemCategory(ItemStack itemStack) {
		Item item = itemStack.getItem();
		if (item instanceof PotatoCannonItem) {
			return ItemCategory.RANGED_WEAPON;
		}
		if (item instanceof ArmItem) {
			return ItemCategory.BLOCK;
		}

		return ItemCategory.NONE;
	}

	public static boolean isAlwaysUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof SymmetryWandItem
				|| item instanceof BlueprintItem
				|| item instanceof ExperienceNuggetItem
				;
	}

	public static boolean isUsableItem(ItemStack itemStack) {
		Item item = itemStack.getItem();
		return item instanceof CartAssemblerBlockItem
				|| item instanceof MinecartContraptionItem
				|| item instanceof BracketBlockItem
				|| item instanceof GogglesItem
				|| item instanceof BlazeBurnerBlockItem
				|| item instanceof BeltConnectorItem
				|| item instanceof SandPaperItem
				|| item instanceof ZapperItem
				|| item instanceof TreeFertilizerItem
				|| item instanceof DisplayLinkBlockItem
				|| item instanceof FilterItem
				|| item instanceof LinkedControllerItem
				|| item instanceof ScheduleItem
				|| item instanceof SchematicItem
				;
	}

	public static Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		Item item = itemStack.getItem();

		if (item instanceof WrenchItem && context.isWithBlock()) {
			BlockState blockState = context.getBlockState();
			if (blockState.getBlock() instanceof IWrenchable wrenchable) {
				if (context.player.isSneaking()) {
					return computeSneakWrench(context, wrenchable);
				} else {
					return computeWrench(context, wrenchable);
				}
			} else {
				if (AllTags.AllBlockTags.WRENCH_PICKUP.matches(blockState)) {
					return Crosshair.INTERACTABLE;
				}
			}
		}

		// contraptions.components.structureMovement.mounted
		if (item instanceof CartAssemblerBlockItem && context.isWithBlock()) {
			BlockPos pos = context.getBlockPos();
			BlockState blockState = context.getBlockState();
			if (blockState.getBlock() instanceof AbstractRailBlock) {
				RailShape shape = MinecartAndRailUtil.getDirectionOfRail(blockState, context.world, pos, null);
				if (shape == RailShape.EAST_WEST || shape == RailShape.NORTH_SOUTH) {
					for (CartAssembleRailType type : CartAssembleRailType.values()) {
						if (type.matches(blockState)) {
							return Crosshair.HOLDING_BLOCK;
						}
					}
				}
			}
		}
		if (item instanceof MinecartContraptionItem && context.isWithBlock()) {
			if (context.getBlockState().isIn(BlockTags.RAILS)) {
				return Crosshair.HOLDING_BLOCK;
			}
		}

		// contraptions.fluids
		if (item instanceof BracketBlockItem bracketBlockItem) {
			BlockState state = context.getBlockState();
			BracketBlock bracketBlock = (BracketBlock) bracketBlockItem.getBlock();
			BracketedTileEntityBehaviour behaviour = TileEntityBehaviour.get(context.world, context.getBlockPos(), BracketedTileEntityBehaviour.TYPE);
			if (behaviour != null && behaviour.canHaveBracket()) {
				Optional<BlockState> suitableBracket = bracketBlock.getSuitableBracket(state, context.getBlockHitSide());
				if (!suitableBracket.isPresent()) {
					suitableBracket = bracketBlock.getSuitableBracket(state, Direction.getEntityFacingOrder(context.player)[0].getOpposite());
				}

				if (suitableBracket.isPresent()) {
					BlockState bracket = behaviour.getBracket();
					BlockState newBracket = suitableBracket.get();
					if (bracket != newBracket) {
						return Crosshair.HOLDING_BLOCK;
					}
				}
			}
		}

		if (item instanceof GogglesItem) {
			EquipmentSlot slot = MobEntity.getPreferredEquipmentSlot(itemStack);
			ItemStack currentEquippedItem = context.player.getEquippedStack(slot);
			if (currentEquippedItem.isEmpty()) {
				return Crosshair.USABLE;
			}
		}

		// processing.burner
		if (item instanceof BlazeBurnerBlockItem burnerItem) {
			if (context.isWithBlock()) {
				if (!burnerItem.hasCapturedBlaze() && context.getBlockEntity() instanceof MobSpawnerBlockEntity) {
					return Crosshair.USABLE;
				}
			} else if (context.isWithEntity()) {
				if (!burnerItem.hasCapturedBlaze() && context.getEntity() instanceof BlazeEntity) {
					return Crosshair.USABLE;
				}
			}
		}

		// relays.belt.item
		if (item instanceof BeltConnectorItem) {
			if (context.player.isSneaking()) {
				return Crosshair.USABLE;
			}
			if (context.isWithBlock() && BeltConnectorItem.validateAxis(context.world, context.getBlockPos())) {
				return Crosshair.HOLDING_BLOCK;
			}
		}

		// curiosities.armor
		if (item instanceof CopperBacktankItem.CopperBacktankBlockItem) {
			if (context.isWithBlock()) {
				return Crosshair.HOLDING_BLOCK;
			}
		}

		// curiosities.tools
		if (item instanceof SandPaperItem) {
			if (context.isWithBlock()) {
				BlockState state = context.getBlockState();
				AxeItemAccessor access = (AxeItemAccessor)Items.DIAMOND_AXE;
				Optional<BlockState> newState = access.porting_lib$getStripped(state);
				if (newState.isPresent()) {
					return Crosshair.USABLE;
				} else {
					newState = Oxidizable.getDecreasedOxidationState(state);
					if (newState.isEmpty()) {
						newState = Optional.ofNullable((Block)((BiMap)HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get()).get(state.getBlock())).map((block) -> {
							return block.getStateWithProperties(state);
						});
					}

					if (newState.isPresent()) {
						return Crosshair.USABLE;
					}
				}
			} else {
				if (itemStack.hasNbt() && itemStack.getOrCreateNbt().contains("Polishing")) {
					return Crosshair.USABLE;
				} else {
					Hand otherHand = context.getHand() == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
					ItemStack itemInOtherHand = context.getItemStack(otherHand);
					if (SandPaperPolishingRecipe.canPolish(context.world, itemInOtherHand)) {
						return Crosshair.USABLE;
					} else {
						HitResult raytraceresult = IItemMixin.invokeRaycast(context.world, context.player, RaycastContext.FluidHandling.NONE);
						if (raytraceresult instanceof BlockHitResult ray) {
							Vec3d hitVec = ray.getPos();
							Box bb = (new Box(hitVec, hitVec)).expand(1.0);

							for (ItemEntity itemEntity : context.world.getNonSpectatingEntities(ItemEntity.class, bb)) {
								if (itemEntity.isAlive() && !(itemEntity.getPos().distanceTo(context.player.getPos()) > 3.0)) {
									ItemStack stack = itemEntity.getStack();
									if (SandPaperPolishingRecipe.canPolish(context.world, stack)) {
										return Crosshair.USABLE;
									}
								}
							}
						}
					}
				}
			}
		}

		// curiosities.weapons
		if (item instanceof PotatoCannonItem) {
			if (!context.player.getArrowType(itemStack).isEmpty()) {
				return Crosshair.RANGED_WEAPON;
			}
		}

		// curiosities.zapper
		if (item instanceof ZapperItem) {
			if (context.isWithBlock()) {
				if (context.player.isSneaking()) {
					return Crosshair.USABLE;
				}
			} else {
				return Crosshair.USABLE;
			}
		}

		if (item instanceof TreeFertilizerItem && context.isWithBlock()) {
			BlockState blockState = context.getBlockState();
			if (blockState.getBlock() instanceof Fertilizable && blockState.isIn(BlockTags.SAPLINGS)) {
				if (!blockState.getOrEmpty(PropaguleBlock.HANGING).orElse(false)) {
					return Crosshair.USABLE;
				}
			}
		}

		// logistics
		if (item instanceof DisplayLinkBlockItem && context.isWithBlock()) {
			return Crosshair.USABLE;
		}

		if (item instanceof ArmItem && context.isWithBlock()) {
			if (ArmInteractionPoint.isInteractable(context.world, context.getBlockPos(), context.getBlockState())) {
				return Crosshair.HOLDING_BLOCK;
			}
		}

		if (item instanceof EjectorItem) {
			if (context.player.isSneaking() && context.isWithBlock()) {
				return Crosshair.USABLE;
			}
		}

		// logistics.item
		if (item instanceof FilterItem) {
			if (!context.player.isSneaking() && context.isMainHand()) {
				return Crosshair.USABLE;
			}
		}

		if (item instanceof LinkedControllerItem) {
			if (context.player.canModifyBlocks() && context.isWithBlock()) {
				BlockState blockState = context.getBlockState();
				if (context.player.isSneaking()) {
					if (AllBlocks.LECTERN_CONTROLLER.has(blockState)) {
						return Crosshair.USABLE;
					}
				} else {
					if (AllBlocks.REDSTONE_LINK.has(blockState)) {
						return Crosshair.INTERACTABLE;
					}
				}

				if (blockState.isOf(Blocks.LECTERN) && !blockState.get(LecternBlock.HAS_BOOK)) {
					return Crosshair.USABLE;
				}

				if (AllBlocks.LECTERN_CONTROLLER.has(blockState)) {
					return null;
				}
			}
			if (context.player.isSneaking() && context.isMainHand()) {
				return Crosshair.USABLE;
			} else if (!context.player.isSneaking()) {
				return Crosshair.USABLE;
			}
		}

		// logistics.trains
		if (item instanceof TrackTargetingBlockItem && context.isWithBlock()) {
			if (context.player.isSneaking() && itemStack.hasNbt()) {
				return Crosshair.USABLE;
			} else {
				if (context.getBlock() instanceof ITrackBlock) {
					return Crosshair.HOLDING_BLOCK;
				} else if (itemStack.hasNbt()) {
					NbtCompound tag = itemStack.getNbt();
					BlockPos selectedPos = NbtHelper.toBlockPos(tag.getCompound("SelectedPos"));
					BlockPos placedPos = context.getBlockPos().offset(context.getBlockHitSide(), context.getBlockState().getMaterial().isReplaceable() ? 0 : 1);
					boolean bezier = tag.contains("Bezier");
					if (selectedPos.isWithinDistance(placedPos, bezier ? 80.0 : 16.0)) {
						return Crosshair.HOLDING_BLOCK;
					}
				}
			}
		}
		if (item instanceof ScheduleItem) {
			if (!context.player.isSneaking() && context.isMainHand()) {
				return Crosshair.USABLE;
			}
		}
		if (item instanceof TrackBlockItem trackItem && context.isWithBlock()) {
			BlockState blockState = context.getBlockState();
			Block block = blockState.getBlock();
			if (context.isMainHand()) {
				if (!trackItem.hasGlint(itemStack)) {
					if (block instanceof TrackBlock track) {
						if (track.getTrackAxes(context.world, context.getBlockPos(), blockState).size() > 1) {
							return Crosshair.HOLDING_BLOCK;
						}
					}
					if (block instanceof ITrackBlock) {
						return Crosshair.HOLDING_BLOCK;
					}
				} else if (context.player.isSneaking()) {
					return Crosshair.USABLE;
				} else {
					if (!(block instanceof ITrackBlock)) {
						BlockState state = trackItem.getPlacementState(new ItemPlacementContext(context.player, Hand.MAIN_HAND, itemStack, context.getBlockHitResult()));
						if (state == null) {
							return null;
						}
					}

					return Crosshair.HOLDING_BLOCK;
				}
			}
		}

		if (item instanceof SchematicItem) {
			if (context.player.isSneaking() && context.isMainHand() && itemStack.hasNbt()) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}

	static Crosshair computeWrench(CrosshairContext context, IWrenchable block) {
		if (block instanceof AttachedActorBlock
				|| block instanceof CasingBlock
				|| block instanceof GirderBlock
				|| block instanceof NozzleBlock
		) {
			return null;
		}

		if (block instanceof BasinBlock
				|| block instanceof BeltFunnelBlock
				|| block instanceof CartAssemblerBlock
				|| block instanceof ControllerRailBlock
				|| block instanceof EncasedPipeBlock
				|| block instanceof FluidTankBlock
				|| block instanceof FunnelBlock
				|| block instanceof GlassFluidPipeBlock
				|| block instanceof RedstoneLinkBlock
				|| block instanceof SignalBlock
				|| block instanceof TrackBlock
		) {
			return Crosshair.USABLE;
		}

		BlockState blockState = context.getBlockState();
		if (block instanceof BeltBlock) {
			if (blockState.get(BeltBlock.CASING)) {
				return Crosshair.USABLE;
			} else if (blockState.get(BeltBlock.PART) == BeltPart.PULLEY) {
				return Crosshair.USABLE;
			} else {
				return null;
			}
		}
		if (block instanceof BeltTunnelBlock) {
			if (!BeltTunnelBlock.hasWindow(blockState)) {
				return null;
			} else {
				return Crosshair.USABLE;
			}
		}
		if (block instanceof ChuteBlock) {
			ChuteBlock.Shape shape = blockState.get(ChuteBlock.SHAPE);
			boolean down = blockState.get(ChuteBlock.FACING) == Direction.DOWN;
			if (down && shape != ChuteBlock.Shape.INTERSECTION) {
				return Crosshair.USABLE;
			}
			return null;
		}
		if (block instanceof DeployerBlock) {
			if (context.getBlockHitSide() == blockState.get(DeployerBlock.FACING)) {
				return Crosshair.USABLE;
			}
		}
		if (block instanceof EncasedCogwheelBlock) {
			if (context.getBlockHitSide().getAxis() == blockState.get(EncasedCogwheelBlock.AXIS)) {
				return Crosshair.USABLE;
			}
		}
		if (block instanceof IWrenchableWithBracket) {
			BracketedTileEntityBehaviour behaviour = BracketedTileEntityBehaviour.get(context.world, context.getBlockPos(), BracketedTileEntityBehaviour.TYPE);
			if (behaviour != null && behaviour.isBracketPresent()) {
				return Crosshair.USABLE;
			}
		}
		if (block instanceof FluidPipeBlock) {
			Direction.Axis axis = FluidPropagator.getStraightPipeAxis(blockState);
			if (axis == null) {
				BlockPos pos = context.getBlockPos();
				Vec3d clickLocation = context.getBlockHitResult().getPos().subtract(pos.getX(), pos.getY(), pos.getZ());
				double closest = 3.4028234663852886E38;
				Direction argClosest = Direction.UP;
				for (Direction direction : Iterate.directions) {
					if (context.getBlockHitSide().getAxis() != direction.getAxis()) {
						Vec3d centerOf = Vec3d.ofCenter(direction.getVector());
						double distance = centerOf.squaredDistanceTo(clickLocation);
						if (distance < closest) {
							closest = distance;
							argClosest = direction;
						}
					}
				}

				axis = argClosest.getAxis();
			}

			if (context.getBlockHitSide().getAxis() == axis) {
				return null;
			} else {
				return Crosshair.USABLE;
			}
		}
		if (block instanceof ItemVaultBlock) {
			if (context.getBlockHitSide().getAxis().isVertical()) {
				return Crosshair.USABLE;
			}
		}
		if (block instanceof MechanicalCrafterBlock) {
			if (context.getBlockHitSide() == blockState.get(MechanicalCrafterBlock.HORIZONTAL_FACING)) {
				return Crosshair.USABLE;
			}
		}
		if (block instanceof MechanicalPistonBlock) {
			if (blockState.get(MechanicalPistonBlock.STATE) != MechanicalPistonBlock.PistonState.RETRACTED) {
				return null;
			}
		}
		if (block instanceof SequencedGearshiftBlock) {
			if (context.getBlockHitSide().getAxis() != Direction.Axis.Y
					&& blockState.get(SequencedGearshiftBlock.HORIZONTAL_AXIS) != context.getBlockHitSide().getAxis()) {
				blockState = blockState.cycle(SequencedGearshiftBlock.VERTICAL);
			}
		}
		if (block instanceof WhistleExtenderBlock) {
			BlockPos findRoot = WhistleExtenderBlock.findRoot(context.world, context.getBlockPos());
			BlockState rootState = context.world.getBlockState(findRoot);
			if (rootState.getBlock() instanceof WhistleBlock whistle) {
				block = whistle;
				blockState = rootState;
			}
		}

		BlockState rotated = block.getRotatedBlockState(blockState, context.getBlockHitSide());
		if (rotated != blockState && rotated.canPlaceAt(context.world, context.getBlockPos())) {
			return Crosshair.USABLE;
		}

		return null;
	}

	static Crosshair computeSneakWrench(CrosshairContext context, IWrenchable block) {
		return Crosshair.USABLE;
	}
}
