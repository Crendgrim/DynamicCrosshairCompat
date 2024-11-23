//? if villagersplus {
package mod.crend.dynamiccrosshair.compat.mixin.villagersplus;

//? if =1.20.1 {
import com.lion.villagersplus.blocks.AlchemistTableBlock;
//?} else
/*import com.finallion.villagersplus.blocks.AlchemistTableBlock;*/
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = AlchemistTableBlock.class, remap = false)
public class AlchemistTableBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
