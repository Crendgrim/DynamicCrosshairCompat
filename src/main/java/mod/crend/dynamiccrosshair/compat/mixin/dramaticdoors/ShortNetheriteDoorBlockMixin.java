//? if dramatic-doors {
package mod.crend.dynamiccrosshair.compat.mixin.dramaticdoors;

import com.fizzware.dramaticdoors.fabric.blockentities.TallNetheriteDoorBlockEntity;
import com.fizzware.dramaticdoors.fabric.blocks.ShortNetheriteDoorBlock;
import com.fizzware.dramaticdoors.fabric.tags.DDItemTags;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ShortNetheriteDoorBlock.class, remap = false)
public class ShortNetheriteDoorBlockMixin extends ShortDoorBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof TallNetheriteDoorBlockEntity door) {
			if (!context.getPlayer().isSpectator()) {
				ItemStack stack = context.getItemStack();
				boolean isKey = stack.isIn(DDItemTags.KEY);
				if (!context.getPlayer().isInSneakingPose() || !isKey || !context.getPlayer().isCreative() && !door.isCorrectKey(stack)) {
					if (door.password == null) {
						if (isKey) {
							return InteractionType.USE_ITEM_ON_BLOCK;
						}
					} else {
						if (context.getPlayer().isCreative() || TallNetheriteDoorBlockEntity.hasKeyInInventory(context.getPlayer(), door.password) == TallNetheriteDoorBlockEntity.KeyStatus.CORRECT_KEY) {
							return InteractionType.INTERACT_WITH_BLOCK;
						}
					}
				} else {
					return InteractionType.INTERACT_WITH_BLOCK;
				}
			}
		}
		return super.dynamiccrosshair$compute(context);
	}
}
//?}
