//? if techreborn {
package mod.crend.dynamiccrosshair.compat.mixin.techreborn;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;
import reborncore.api.ToolManager;
import techreborn.blocks.misc.BlockAlarm;

@Mixin(value = BlockAlarm.class, remap = false)
public class BlockAlarmMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (ToolManager.INSTANCE.canHandleTool(context.getItemStack())) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return context.getPlayer().isSneaking() ? InteractionType.INTERACT_WITH_BLOCK : InteractionType.EMPTY;
	}
}
//?}
