//? if macaws_bridges {
package mod.crend.dynamiccrosshair.compat.mixin.mcwbridges;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.kikoz.mcwbridges.objects.Bridge_Stairs;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = Bridge_Stairs.class, remap = false)
public class Bridge_StairsMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItem() instanceof BlockItem blockItem && blockItem.getBlock() == context.getBlock()) {
			Direction facing = context.getPlayer().getHorizontalFacing();
			BlockPos placePos = context.getBlockPos().offset(facing);
			if (context.getWorld().getBlockState(placePos).isAir()) {
				return InteractionType.PLACE_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
