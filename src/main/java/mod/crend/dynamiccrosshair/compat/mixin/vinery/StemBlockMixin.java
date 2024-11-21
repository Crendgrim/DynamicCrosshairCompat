//? if vinery {
package mod.crend.dynamiccrosshair.compat.mixin.vinery;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import satisfyu.vinery.block.stem.StemBlock;

@Mixin(value = StemBlock.class, remap = false)
public abstract class StemBlockMixin implements DynamicCrosshairBlock {
	@Shadow public abstract boolean hasTrunk(World world, BlockPos pos);

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isMainHand()) {
			BlockState blockState = context.getBlockState();
			ItemStack itemStack = context.getItemStack();
			int age = blockState.get(StemBlock.AGE);
			if (age > 0 && itemStack.isOf(Items.SHEARS)) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
			if (age > 3) {
				return InteractionType.TAKE_ITEM_FROM_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
