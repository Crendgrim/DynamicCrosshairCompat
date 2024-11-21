//? if expanded-delight {
package mod.crend.dynamiccrosshair.compat.mixin.expandeddelight;

import com.ianm1647.expandeddelight.block.custom.JuicerBlock;
import com.ianm1647.expandeddelight.block.entity.JuicerBlockEntity;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = JuicerBlock.class, remap = false)
public class JuicerBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (context.getBlockEntity() instanceof JuicerBlockEntity juicerBlockEntity
				&& context.getItemStack().isOf(Items.GLASS_BOTTLE)
				&& !juicerBlockEntity.getStack(3).isEmpty()
		) {
			return InteractionType.FILL_ITEM_FROM_BLOCK;
		}
		return InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
