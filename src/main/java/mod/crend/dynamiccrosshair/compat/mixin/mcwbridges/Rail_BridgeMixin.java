//? if macaws_bridges {
package mod.crend.dynamiccrosshair.compat.mixin.mcwbridges;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.kikoz.mcwbridges.objects.Rail_Bridge;
import net.minecraft.item.Items;
import net.minecraft.state.property.BooleanProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = Rail_Bridge.class, remap = false)
public class Rail_BridgeMixin implements DynamicCrosshairBlock {
	@Shadow @Final public static BooleanProperty POWERED;

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockState().get(POWERED)) {
			if (context.isEmptyHanded()) {
				return InteractionType.INTERACT_WITH_BLOCK;
			}
		} else {
			if (context.getItemStack().isOf(Items.REDSTONE_TORCH)) {
				return InteractionType.USE_ITEM_ON_BLOCK;
			}
		}
		return InteractionType.EMPTY;
	}
}
//?}
