//? if universal-graves {
package mod.crend.dynamiccrosshair.compat.mixin.universal_graves;

import eu.pb4.graves.registry.VisualGraveBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = VisualGraveBlock.class, remap = false)
public class VisualGraveBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (itemStack.isOf(Items.FEATHER)
				|| itemStack.isOf(Items.PLAYER_HEAD)
				|| itemStack.isOf(Items.MOSS_BLOCK)
				|| itemStack.isOf(Items.SPONGE)
				|| itemStack.isOf(Items.WET_SPONGE)
				|| itemStack.getItem() instanceof ShovelItem
		) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
