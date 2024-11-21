//? if paladins-furniture {
package mod.crend.dynamiccrosshair.compat.mixin.pfm;

import com.unlikepaladin.pfm.blocks.ClassicNightstandBlock;
import com.unlikepaladin.pfm.blocks.FreezerBlock;
import com.unlikepaladin.pfm.blocks.FridgeBlock;
import com.unlikepaladin.pfm.blocks.KitchenCabinetBlock;
import com.unlikepaladin.pfm.blocks.KitchenDrawerBlock;
import com.unlikepaladin.pfm.blocks.LightSwitchBlock;
import com.unlikepaladin.pfm.blocks.MicrowaveBlock;
import com.unlikepaladin.pfm.blocks.StoveBlock;
import com.unlikepaladin.pfm.blocks.WorkingTableBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = {
		ClassicNightstandBlock.class,
		FreezerBlock.class,
		FridgeBlock.class,
		KitchenCabinetBlock.class,
		KitchenDrawerBlock.class,
		LightSwitchBlock.class,
		MicrowaveBlock.class,
		StoveBlock.class,
		WorkingTableBlock.class
}, remap = false)
public class AlwaysInteractableBlocksMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
