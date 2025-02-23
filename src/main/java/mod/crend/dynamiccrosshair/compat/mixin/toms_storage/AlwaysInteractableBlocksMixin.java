//? if toms-storage {
package mod.crend.dynamiccrosshair.compat.mixin.toms_storage;

import com.tom.storagemod.block.AbstractStorageTerminalBlock;
//? if <1.21
import com.tom.storagemod.block.FilteredInventoryCableConnectorBlock;
import com.tom.storagemod.block.InventoryConnectorBlock;
import com.tom.storagemod.block.LevelEmitterBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = {
		AbstractStorageTerminalBlock.class,
		//? if <1.21
		FilteredInventoryCableConnectorBlock.class,
		InventoryConnectorBlock.class,
		LevelEmitterBlock.class
}, remap = false)
public class AlwaysInteractableBlocksMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
