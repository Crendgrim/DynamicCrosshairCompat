package mod.crend.dynamiccrosshair.compat.mixin.create;

import com.simibubi.create.content.kinetics.gantry.GantryShaftBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = GantryShaftBlock.class, remap = false)
public interface GantryShaftBlockAccessor {
	@Accessor
	static int getPlacementHelperId() {
		throw new RuntimeException();
	}
}
