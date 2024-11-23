//? if ae2 {
package mod.crend.dynamiccrosshair.compat.mixin.ae2;

import appeng.block.crafting.AbstractCraftingUnitBlock;
import appeng.blockentity.crafting.CraftingBlockEntity;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = AbstractCraftingUnitBlock.class, remap = false)
public class AbstractCraftingUnitBlockMixin extends AEBaseEntityBlockMixin {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof CraftingBlockEntity tg) {
			if (!context.getPlayer().isSneaking() && tg.isFormed() && tg.isActive()) {
				return InteractionType.INTERACT_WITH_BLOCK;
			}
		}
		return super.dynamiccrosshair$compute(context);
	}
}
//?}
