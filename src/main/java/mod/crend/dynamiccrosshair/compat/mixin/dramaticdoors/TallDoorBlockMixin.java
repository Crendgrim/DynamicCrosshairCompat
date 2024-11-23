//? if dramatic-doors {
package mod.crend.dynamiccrosshair.compat.mixin.dramaticdoors;

import com.fizzware.dramaticdoors.fabric.blocks.TallDoorBlock;
import com.fizzware.dramaticdoors.fabric.compat.Compats;
import com.fizzware.dramaticdoors.fabric.compat.registries.SupplementariesCompat;
import com.fizzware.dramaticdoors.fabric.tags.DDBlockTags;
import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = TallDoorBlock.class, remap = false)
public class TallDoorBlockMixin implements DynamicCrosshairBlock {
	@Shadow @Final private BlockSetType type;

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		if (type.canOpenByHand() || blockState.isIn(DDBlockTags./*? if <1.21.1 {*/HAND_OPENABLE_TALL_METAL_DOORS/*?} else {*//*MOB_INTERACTABLE_TALL_DOORS*//*?}*/)) {
			if (Compats.SUPPLEMENTARIES_INSTALLED) {
				if (blockState.getBlock() == SupplementariesCompat.TALL_GOLD_DOOR && blockState.get(TallDoorBlock.POWERED)) {
					return InteractionType.EMPTY;
				}
			}
			//? if <1.20.6 {
			if (blockState.getBlock() == SupplementariesCompat.TALL_SILVER_DOOR && !blockState.get(TallDoorBlock.POWERED)) {
				return InteractionType.EMPTY;
			}
			//?}
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		return InteractionType.EMPTY;
	}
}
//?}
