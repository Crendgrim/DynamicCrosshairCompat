//? if meadow {
package mod.crend.dynamiccrosshair.compat.mixin.meadow;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.satisfy.meadow.block.FireLog;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = FireLog.class, remap = false)
public class FireLogMixin extends Block implements DynamicCrosshairBlock {
	public FireLogMixin(Settings settings) {
		super(settings);
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		int stage = context.getBlockState().get(FireLog.STAGE);
		ItemStack itemStack = context.getItemStack();
		if (itemStack.isOf(asItem())) {
			if (stage < 3 && stage > 0) {
				return InteractionType.PLACE_BLOCK;
			}
		} else if (itemStack.isOf(Items.IRON_AXE) && stage == 1 && itemStack.getDamage() == 0) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		} else if (context.getPlayer().isSneaking() && itemStack.isEmpty()) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
