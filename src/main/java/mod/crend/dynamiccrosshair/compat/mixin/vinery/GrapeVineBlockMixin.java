//? if vinery {
package mod.crend.dynamiccrosshair.compat.mixin.vinery;

import mod.crend.dynamiccrosshair.compat.helper.VineryHandler;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.Items;
import net.satisfy.vinery.block.grape.GrapeVineBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = GrapeVineBlock.class, remap = false)
public class GrapeVineBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isMainHand() && context.getItemStack().isOf(Items.SHEARS)) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return VineryHandler.fromBush(context.getBlockState(), context.getItemStack(), GrapeVineBlock.AGE, 3);
	}
}
//?}
