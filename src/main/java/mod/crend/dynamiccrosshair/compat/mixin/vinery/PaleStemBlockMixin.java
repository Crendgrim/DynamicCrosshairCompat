//? if vinery {
package mod.crend.dynamiccrosshair.compat.mixin.vinery;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.satisfy.vinery.block.stem.PaleStemBlock;
import net.satisfy.vinery.block.stem.StemBlock;
import net.satisfy.vinery.item.GrapeBushSeedItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = PaleStemBlock.class, remap = false)
public abstract class PaleStemBlockMixin extends StemBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isMainHand()) {
			int age = context.getBlockState().get(StemBlock.AGE);
			if (age == 0 && context.getItemStack().getItem() instanceof GrapeBushSeedItem seed) {
				if (this.hasTrunk(context.getWorld(), context.getBlockPos()) && seed.getType()./*? if =1.20.1 {*/isLattice/*?} else {*//*isPaleType*//*?}*/()) {
					return InteractionType.PLACE_ITEM_ON_BLOCK;
				}
			}
		}
		return super.dynamiccrosshair$compute(context);
	}
}
//?}
