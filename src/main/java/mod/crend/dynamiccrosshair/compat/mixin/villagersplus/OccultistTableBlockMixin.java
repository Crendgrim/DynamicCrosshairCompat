//? if villagersplus {
package mod.crend.dynamiccrosshair.compat.mixin.villagersplus;

//? if =1.20.1 {
import com.lion.villagersplus.blocks.OccultistTableBlock;
//?} else
/*import com.finallion.villagersplus.blocks.OccultistTableBlock;*/
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = OccultistTableBlock.class, remap = false)
public class OccultistTableBlockMixin implements DynamicCrosshairBlock {
	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return InteractionType.INTERACT_WITH_BLOCK;
	}
}
//?}
