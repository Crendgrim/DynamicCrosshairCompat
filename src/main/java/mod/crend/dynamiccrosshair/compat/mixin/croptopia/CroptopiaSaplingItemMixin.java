//? if croptopia {
package mod.crend.dynamiccrosshair.compat.mixin.croptopia;

import com.epherical.croptopia.items.CroptopiaSaplingItem;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = CroptopiaSaplingItem.class, remap = false)
public class CroptopiaSaplingItemMixin implements DynamicCrosshairItem {
	@Shadow @Final private Block vanillaLeafBlock;

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.includeUsableItem() && context.isWithBlock()) {
			if (context.getBlock() == this.vanillaLeafBlock) {
				return InteractionType.PLACE_BLOCK;
			}
		}
		return null;
	}
}
//?}
