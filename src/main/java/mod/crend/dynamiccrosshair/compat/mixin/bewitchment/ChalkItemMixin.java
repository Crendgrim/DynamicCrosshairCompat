//? if bewitchment {
package mod.crend.dynamiccrosshair.compat.mixin.bewitchment;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import moriyashiine.bewitchment.common.item.ChalkItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ChalkItem.class, remap = false)
public class ChalkItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock()) {
			ItemPlacementContext placementContext = new ItemPlacementContext(context.getWorld(), context.getPlayer(), context.getHand(), context.getItemStack(), context.getBlockHitResult());
			BlockPos pos = context.getBlockPos();
			if (!context.getWorld().getBlockState(pos).canReplace(placementContext)) {
				pos = pos.offset(context.getBlockHitSide());
			}

			if (context.getWorld().getBlockState(pos).canReplace(placementContext)) {
				return InteractionType.PLACE_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
