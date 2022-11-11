package mod.crend.dynamiccrosshair.compat.anotherfurniture;

import com.starfish_studios.another_furniture.AnotherFurniture;
import com.starfish_studios.another_furniture.block.*;
import com.starfish_studios.another_furniture.entity.SeatEntity;
import com.starfish_studios.another_furniture.registry.AFBlockTags;
import com.starfish_studios.another_furniture.registry.AFItemTags;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import mod.crend.dynamiccrosshair.mixin.IAbstractBlockMixin;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

public class ApiImplAnotherFurniture implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return AnotherFurniture.MOD_ID;
	}

	@Override
	public boolean isAlwaysInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return (    block instanceof CurtainBlock
				||  block instanceof DrawerBlock
				|| (block instanceof ShutterBlock && ((IAbstractBlockMixin) block).getMaterial() != Material.METAL)
		);
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		Block block = blockState.getBlock();
		return block instanceof SeatBlock
				|| block instanceof CurtainBlock
				|| block instanceof DrawerBlock
				|| block instanceof LampBlock
				|| block instanceof PlanterBoxBlock
				|| block instanceof ServiceBellBlock
				|| block instanceof ShelfBlock
				|| block instanceof ShutterBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		BlockState blockState = context.getBlockState();
		Block block = blockState.getBlock();
		BlockPos pos = context.getBlockPos();

		if (block instanceof SeatBlock seat) {
			if (block instanceof BenchBlock) {
				if (itemStack.isIn(AFItemTags.FURNITURE_HAMMER)) {
					return Crosshair.USABLE;
				}
			}
			if (block instanceof ChairBlock chair) {
				boolean tucked = blockState.get(ChairBlock.TUCKED);
				if ((context.player.isInSneakingPose() || tucked) && chair.canTuckUnderFacing(blockState, context.world, pos)) {
					if (tucked || chair.isChairBlocking(blockState, context.world, pos)) {
						return Crosshair.INTERACTABLE;
					}
				}
			}
			// common seat block
			BlockState above = context.world.getBlockState(pos.up());
			if (seat.isSittable(blockState)
					&& !context.player.hasVehicle()
					&& !context.player.isInSneakingPose()
					&& (above.getCollisionShape(context.world, pos).isEmpty() || above.isIn(AFBlockTags.NO_SEAT_COLLISION_CHECK))
					&& context.world.getNonSpectatingEntities(SeatEntity.class, new Box(pos, pos.add(1, 1, 1))).isEmpty()
			) {
				return Crosshair.INTERACTABLE;
			}
		}
		if (block instanceof LampBlock) {
			if (!itemStack.isIn(AFItemTags.LAMPS) || blockState.get(LampBlock.FACING) != Direction.UP || context.getBlockHitSide() != Direction.UP) {
				return Crosshair.INTERACTABLE;
			}
		}
		if (block instanceof PlanterBoxBlock) {
			if (itemStack.isIn(AFItemTags.PLANTER_BOX_PLACEABLES) && !itemStack.isIn(AFItemTags.PLANTER_BOX_BANNED)) {
				return Crosshair.USABLE;
			}
		}
		if (block instanceof ServiceBellBlock) {
			if (!blockState.get(ServiceBellBlock.POWERED)) {
				return Crosshair.INTERACTABLE;
			}
		}
		if (block instanceof ShelfBlock) {
			if (context.getBlockHitSide() == Direction.UP) {
				if (itemStack.isEmpty()) {
					return Crosshair.INTERACTABLE;
				}
				return Crosshair.USABLE;
			}
		}

		return null;
	}
}
