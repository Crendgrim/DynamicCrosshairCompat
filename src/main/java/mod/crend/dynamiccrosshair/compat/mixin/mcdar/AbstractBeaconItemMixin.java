//? if mc-dungeons-artifacts {
package mod.crend.dynamiccrosshair.compat.mixin.mcdar;

import chronosacaria.mcdar.artifacts.beacon.AbstractBeaconItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = AbstractBeaconItem.class, remap = false)
public abstract class AbstractBeaconItemMixin implements DynamicCrosshairItem {
	@Shadow public abstract boolean canFire(PlayerEntity playerEntity, ItemStack itemStack);

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (this.canFire(context.getPlayer(), context.getItemStack())) {
			return InteractionType.USE_ITEM;
		}
		return InteractionType.EMPTY;
	}
}
//?}
