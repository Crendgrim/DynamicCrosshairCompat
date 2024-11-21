//? if macaws_bridges {
package mod.crend.dynamiccrosshair.compat.mixin.mcwbridges;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.kikoz.mcwbridges.objects.Bridge_Base;
import net.kikoz.mcwbridges.objects.Plier;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = Bridge_Base.class, remap = false)
public class Bridge_BaseMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getItem() instanceof Plier) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		if (context.getBlockState().get(Bridge_Base.TORCH)) {
			return InteractionType.INTERACT_WITH_BLOCK;
		} else if (context.getItemStack().isOf(Items.TORCH)) {
			return InteractionType.PLACE_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
