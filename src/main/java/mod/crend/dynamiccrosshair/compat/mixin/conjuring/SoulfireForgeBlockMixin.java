//? if conjuring {
package mod.crend.dynamiccrosshair.compat.mixin.conjuring;

import com.glisco.conjuring.blocks.soulfire_forge.SoulfireForgeBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = SoulfireForgeBlock.class, remap = false)
public class SoulfireForgeBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItemStack().isOf(Items.FLINT_AND_STEEL) && !context.getBlockState().get(SoulfireForgeBlock.BURNING)) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return null;
	}
}
//?}
