//? if conjuring {
package mod.crend.dynamiccrosshair.compat.mixin.conjuring;

import com.glisco.conjuring.items.ConjurationEssence;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.registry.tag.BlockTags;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ConjurationEssence.class, remap = false)
public class ConjurationEssenceMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getPlayer().isSneaking() && context.isWithBlock() && context.getBlockState().isIn(BlockTags.BASE_STONE_OVERWORLD)) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
