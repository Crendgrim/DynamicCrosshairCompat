//? if bewitchment {
package mod.crend.dynamiccrosshair.compat.mixin.bewitchment;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import moriyashiine.bewitchment.common.block.BrazierBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BrazierBlock.class, remap = false)
public class BrazierBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		if (!blockState.get(Properties.WATERLOGGED)) {
			if (blockState.get(Properties.LIT)) {
				return InteractionType.INTERACT_WITH_BLOCK;
			}
			ItemStack itemStack = context.getItemStack();
			if (itemStack.isOf(Items.FLINT_AND_STEEL)) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
			if (!itemStack.isEmpty()) {
				return InteractionType.PLACE_ITEM_ON_BLOCK;
			}
			return InteractionType.TAKE_ITEM_FROM_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
