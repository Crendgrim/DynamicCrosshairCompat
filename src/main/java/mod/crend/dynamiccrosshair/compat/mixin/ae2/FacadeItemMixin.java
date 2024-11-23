//? if ae2 {
package mod.crend.dynamiccrosshair.compat.mixin.ae2;

import appeng.api.parts.IPartHost;
import appeng.api.parts.PartHelper;
import appeng.facade.FacadePart;
import appeng.items.parts.FacadeItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = FacadeItem.class, remap = false)
public abstract class FacadeItemMixin implements DynamicCrosshairItem {
	@Shadow public abstract FacadePart createPartFromItemStack(ItemStack is, Direction side);

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.isWithBlock()) {
			FacadePart facade = this.createPartFromItemStack(context.getItemStack(), context.getBlockHitSide());
			IPartHost host = PartHelper.getPartHost(context.getWorld(), context.getBlockPos());
			if (facade != null && host != null && FacadeItem.canPlaceFacade(host, facade)) {
				return InteractionType.PLACE_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
