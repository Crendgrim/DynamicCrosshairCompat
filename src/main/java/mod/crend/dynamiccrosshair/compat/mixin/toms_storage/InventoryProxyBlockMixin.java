//? if toms-storage {
package mod.crend.dynamiccrosshair.compat.mixin.toms_storage;

import com.tom.storagemod.block.InventoryProxyBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = InventoryProxyBlock.class, remap = false)
public class InventoryProxyBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItemStack().isOf(Items.DIAMOND) && context.getBlockState().get(InventoryProxyBlock.FACING) != context.getBlockHitSide()) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
