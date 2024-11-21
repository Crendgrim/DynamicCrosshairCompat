package mod.crend.dynamiccrosshair.compat.helper;

import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.IntProperty;

public class VineryHandler {
	public static InteractionType fromBush(BlockState blockState, ItemStack itemStack, IntProperty ageProperty, int maxAge) {
		int age = blockState.get(ageProperty);
		if (age != maxAge && itemStack.isOf(Items.BONE_MEAL)) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		} else if (age > 1) {
			return InteractionType.TAKE_ITEM_FROM_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
