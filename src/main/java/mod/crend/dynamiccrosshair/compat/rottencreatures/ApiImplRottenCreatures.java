package mod.crend.dynamiccrosshair.compat.rottencreatures;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.common.blocks.TntBarrelBlock;
import com.github.teamfusion.rottencreatures.common.item.TreasureChestItem;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairApi;
import mod.crend.dynamiccrosshair.component.Crosshair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class ApiImplRottenCreatures implements DynamicCrosshairApi {
	@Override
	public String getNamespace() {
		return RottenCreatures.MOD_ID;
	}

	@Override
	public boolean isInteractableBlock(BlockState blockState) {
		return blockState.getBlock() instanceof TntBarrelBlock;
	}

	@Override
	public Crosshair computeFromBlock(CrosshairContext context) {
		Block block = context.getBlock();
		ItemStack itemStack = context.getItemStack();

		if (block instanceof TntBarrelBlock) {
			if (itemStack.isOf(Items.FLINT_AND_STEEL) || itemStack.isOf(Items.FIRE_CHARGE) || itemStack.isOf(Items.FIREWORK_ROCKET)) {
				return Crosshair.USABLE;
			}
		}

		return null;
	}

	@Override
	public Crosshair computeFromItem(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (itemStack.getItem() instanceof TreasureChestItem && context.isWithBlock() && context.includeHoldingBlock()) {
			if (context.getBlockHitSide() != Direction.DOWN) {
				BlockPos pos = context.getBlockPos();
				Vec3d position = Vec3d.ofBottomCenter(pos);
				Box boundingBox = RCEntityTypes.TREASURE_CHEST.get().getDimensions().getBoxAt(position.getX(), position.getY(), position.getZ());
				if (context.world.isSpaceEmpty(null, boundingBox) && context.world.getOtherEntities(null, boundingBox).isEmpty()) {
					return Crosshair.HOLDING_BLOCK;
				}
			}
		}
		return null;
	}
}
