//? if macaws_roofs {
package mod.crend.dynamiccrosshair.compat.mixin.mcwroofs;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.kikoz.mcwroofs.objects.gutters.GutterTall;
import net.kikoz.mcwroofs.objects.gutters.RainGutter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.BooleanProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = {
		RainGutter.class,
		GutterTall.class
}, remap = false)
public class GutterMixin implements DynamicCrosshairBlock {
	@Shadow @Final private static BooleanProperty WATER;

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		boolean hasWater = context.getBlockState().get(WATER);
		ItemStack itemStack = context.getItemStack();
		if (!hasWater && itemStack.isOf(Items.WATER_BUCKET)) {
			return InteractionType.FILL_BLOCK_FROM_ITEM;
		}
		if (hasWater && (itemStack.isOf(Items.GLASS_BOTTLE) || itemStack.isOf(Items.BUCKET))) {
			return InteractionType.FILL_ITEM_FROM_BLOCK;
		}
		return null;
	}
}
//?}
