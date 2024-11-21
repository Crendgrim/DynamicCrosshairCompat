//? if kibe {
package mod.crend.dynamiccrosshair.compat.mixin.kibe;

import io.github.lucaargolo.kibe.blocks.entangledtank.EntangledTank;
import io.github.lucaargolo.kibe.blocks.entangledtank.EntangledTankEntity;
import io.github.lucaargolo.kibe.items.miscellaneous.Rune;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.fabric.CrosshairFluidContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EntangledTank.class, remap = false)
public class EntangledTankMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getHitResult().getPos().getY() - context.getBlockPos().getY() > 0.9375) {
			Item item = context.getItem();
			if (item instanceof Rune || item == Items.DIAMOND || item == Items.GOLD_INGOT) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}
		if (context.getBlockEntity() instanceof EntangledTankEntity blockEntity && CrosshairFluidContext.canInteractWithFluidStorage(context, blockEntity.getTank())) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
