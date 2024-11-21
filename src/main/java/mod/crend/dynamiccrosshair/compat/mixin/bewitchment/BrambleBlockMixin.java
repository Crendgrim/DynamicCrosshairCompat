//? if bewitchment {
package mod.crend.dynamiccrosshair.compat.mixin.bewitchment;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import moriyashiine.bewitchment.common.block.BrambleBlock;
import moriyashiine.bewitchment.common.registry.BWProperties;
import net.minecraft.item.BoneMealItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BrambleBlock.Fruiting.class, remap = false)
public class BrambleBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockState().get(BWProperties.HAS_FRUIT)) {
			return InteractionType.TAKE_ITEM_FROM_BLOCK;
		}
		if (context.getItem() instanceof BoneMealItem) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
