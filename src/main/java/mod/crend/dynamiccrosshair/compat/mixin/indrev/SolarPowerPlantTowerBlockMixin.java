//? if industrial-revolution {
package mod.crend.dynamiccrosshair.compat.mixin.indrev;

import me.steven.indrev.IndustrialRevolution;
import me.steven.indrev.blocks.machine.solarpowerplant.SolarPowerPlantTowerBlock;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = SolarPowerPlantTowerBlock.class, remap = false)
public class SolarPowerPlantTowerBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if (itemStack.isIn(IndustrialRevolution.INSTANCE.getSCREWDRIVER_TAG())
				&& itemStack.hasNbt()
				&& itemStack.getNbt().contains("SelectedHeliostats")
		) {
			return InteractionType.USE_ITEM_ON_BLOCK;
		}
		return InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
