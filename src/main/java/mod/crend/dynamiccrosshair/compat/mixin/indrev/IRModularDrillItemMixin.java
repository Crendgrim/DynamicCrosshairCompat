//? if industrial-revolution {
package mod.crend.dynamiccrosshair.compat.mixin.indrev;

import me.steven.indrev.items.energy.IRModularDrillItem;
import me.steven.indrev.tools.modular.DrillModule;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = IRModularDrillItem.class, remap = false)
public class IRModularDrillItemMixin implements DynamicCrosshairItem {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		ItemStack itemStack = context.getItemStack();
		if ((!context.isWithBlock() && DrillModule.CONTROLLED_DESTRUCTION.getLevel(itemStack) > 0) || DrillModule.MATTER_PROJECTOR.getLevel(itemStack) > 0) {
			return InteractionType.USE_ITEM;
		}
		return InteractionType.EMPTY;
	}
}
//?}
