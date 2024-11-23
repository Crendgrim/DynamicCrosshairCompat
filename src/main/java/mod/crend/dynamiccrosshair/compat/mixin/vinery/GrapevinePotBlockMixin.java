//? if vinery {
package mod.crend.dynamiccrosshair.compat.mixin.vinery;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.IntProperty;
import net.satisfy.vinery.block.GrapevinePotBlock;
import net.satisfy.vinery.item.GrapeItem;
import net.satisfy.vinery.registry.ObjectRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = GrapevinePotBlock.class, remap = false)
public abstract class GrapevinePotBlockMixin implements DynamicCrosshairBlock {

	@Shadow @Final private static IntProperty STAGE;
	@Shadow @Final private static IntProperty STORAGE;
	@Shadow protected abstract boolean canTakeWine(BlockState state, ItemStack stackInHand);

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		ItemStack itemStack = context.getItemStack();
		if (itemStack.getItem() instanceof GrapeItem) {
			if (blockState.get(STAGE) <= 3 && blockState.get(STORAGE) < 9) {
				return InteractionType.PLACE_ITEM_ON_BLOCK;
			}
		} else if (itemStack.isOf(ObjectRegistry.WINE_BOTTLE.get().asItem()) && this.canTakeWine(blockState, itemStack)) {
			return InteractionType.FILL_ITEM_FROM_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
