//? if macaws_bridges {
package mod.crend.dynamiccrosshair.compat.mixin.mcwbridges;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.kikoz.mcwbridges.init.ItemInit;
import net.kikoz.mcwbridges.objects.Bridge_Block;
import net.kikoz.mcwbridges.objects.Bridge_Block_Rope;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = {
		Bridge_Block.class,
		Bridge_Block_Rope.class
}, remap = false)
public class Bridge_BlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		Item item = context.getItem();
		if (item == ItemInit.PLIERS.asItem() || item == Items.SHEARS) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		if (item instanceof BlockItem blockItem && blockItem.getBlock() == context.getBlock()) {
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
