//? if techreborn {
package mod.crend.dynamiccrosshair.compat.mixin.techreborn;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import org.spongepowered.asm.mixin.Mixin;
import reborncore.api.ToolManager;
import techreborn.blocks.lighting.LampBlock;
import techreborn.blocks.storage.energy.EnergyStorageBlock;
import techreborn.blocks.storage.energy.LSUStorageBlock;
import techreborn.blocks.transformers.BlockTransformer;

@Mixin(value = {
		LampBlock.class,
		LSUStorageBlock.class,
		BlockTransformer.class,
		EnergyStorageBlock.class,
}, remap = false)
public class CanHandleWithToolsBlocksMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (ToolManager.INSTANCE.canHandleTool(context.getItemStack())) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
