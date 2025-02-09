//? if vinery {
package mod.crend.dynamiccrosshair.compat.mixin.vinery;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.satisfy.vinery.core.block.LatticeBlock;
import net.satisfy.vinery.core.block.StemBlock;
import net.satisfy.vinery.core.item.GrapeBushSeedItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = LatticeBlock.class, remap = false)
public abstract class LatticeBlockMixin extends StemBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isMainHand()) {
			int age = context.getBlockState().get(StemBlock.AGE);
			if (age == 0 && context.getItemStack().getItem() instanceof GrapeBushSeedItem seed) {
				if (!seed.getType()./*? if =1.20.1 {*/isLattice/*?} else {*//*isPaleType*//*?}*/()) {
					return InteractionType.PLACE_ITEM_ON_BLOCK;
				}
			}
		}
		return super.dynamiccrosshair$compute(context);
	}
}
//?}
