//? if zenith {
package mod.crend.dynamiccrosshair.compat.mixin.zenith;

import dev.shadowsoffire.apotheosis.garden.EnderLeadItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EnderLeadItem.class, remap = false)
public class EnderLeadItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock()) {
			NbtCompound tag = context.getItemStack().getOrCreateSubNbt("entity_data");
			if (!tag.isEmpty()) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
