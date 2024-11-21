//? if extended-drawers {
package mod.crend.dynamiccrosshair.compat.mixin.extended_drawers;

import io.github.mattidragon.extendeddrawers.block.ShadowDrawerBlock;
import io.github.mattidragon.extendeddrawers.block.entity.ShadowDrawerBlockEntity;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ShadowDrawerBlock.class, remap = false)
public abstract class ShadowDrawerBlockMixin implements DynamicCrosshairBlock {
	@Shadow public abstract boolean isFront(BlockState state, Direction direction);

	@Override
	@SuppressWarnings("UnstableApiUsage")
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isMainHand() && this.isFront(context.getBlockState(), context.getBlockHitSide())) {
			ShadowDrawerBlockEntity drawerBlockEntity = (ShadowDrawerBlockEntity) context.getBlockEntity();

			ItemStack handItemStack = context.getItemStack();
			if (context.getPlayer().isSneaking()) {
				if (handItemStack.isEmpty()) {
					return InteractionType.INTERACT_WITH_BLOCK;
				}
			} else {
				if (handItemStack.isEmpty()) {
					if (!drawerBlockEntity.item.isBlank()) {
						return InteractionType.INTERACT_WITH_BLOCK;
					}
				} else {
					if (drawerBlockEntity.item.isBlank() || drawerBlockEntity.item.getItem() == handItemStack.getItem()) {
						return InteractionType.PLACE_ITEM_ON_BLOCK;
					}
				}
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
