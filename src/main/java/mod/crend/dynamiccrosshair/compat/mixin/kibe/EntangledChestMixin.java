//? if kibe {
package mod.crend.dynamiccrosshair.compat.mixin.kibe;

import io.github.lucaargolo.kibe.blocks.entangledchest.EntangledChest;
import io.github.lucaargolo.kibe.items.miscellaneous.Rune;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EntangledChest.class, remap = false)
public class EntangledChestMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getHitResult().getPos().getY() - context.getBlockPos().getY() > 0.9375) {
			Item item = context.getItem();
			if (item instanceof Rune || item == Items.DIAMOND || item == Items.GOLD_INGOT) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}
		if (EntangledChest.Companion.canOpen(context.getWorld(), context.getBlockPos())) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
